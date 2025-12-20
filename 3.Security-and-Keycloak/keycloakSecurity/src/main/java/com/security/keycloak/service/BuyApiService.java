package com.security.keycloak.service;

import java.util.List;
import java.util.Map;

import com.security.keycloak.repository.ClientApisRepo;
import com.security.keycloak.repository.UserApisRepo;
import com.security.keycloak.repository.UserRepo;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.entity.Client;
import com.security.keycloak.entity.ClientApis;
import com.security.keycloak.entity.User;
import com.security.keycloak.entity.UserApis;

@Service
public class BuyApiService {

    @Autowired
    private ClientApisRepo clientApisRepo;
    
    @Autowired
    private UserApisRepo userApisRepo;
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClientService clientService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApisService apisService;

    public String buyApis(List<Map<String, Object>> apisWithNoOfHits, String userId, String userName){

        
        for (Map<String, Object> apiWithNoOfHits : apisWithNoOfHits) {
			int apiId = (int) apiWithNoOfHits.get("apiId");
			var apiName = apiWithNoOfHits.get("apiName").toString();
			int noOfHits = (int) apiWithNoOfHits.get("noOfHits");
			System.out.println("ApiId :: " + apiId + ", apiName :: " + apiName + ", No of Hits :: " + noOfHits);

			//ClientApis clientApis = new ClientApis();
			UserApis userApis = new UserApis();

			Apis apis = new Apis();
			apis.setId(apiId);

			User user = new User();
			user = userRepo.findByUserName(userName); //finding user details from User Table

//			Client client = clientService.readClient(clientId);
//			client.setTotalRemainingHits(client.getTotalRemainingHits()+noOfHits);
//            clientService.updateClient(client);

            userApis.setApi(apis);
           // clientApis.setClient(client);
            userApis.setUser(user);  	//setting user details in UserApis Entity
            userApis.setRemainingHitsOfApi(noOfHits);
            
            UserApis byApiAndUser = userApisRepo.findByApiAndUser(apis, user); //checking whether user already bought api. If yes then add number to remaining hits and save, if not then save as a seperate entity
            if(byApiAndUser == null) {
            	userApisRepo.save(userApis);	
            }
            else {
            	byApiAndUser.setRemainingHitsOfApi(byApiAndUser.getRemainingHitsOfApi()+noOfHits);
            	userApisRepo.save(byApiAndUser);
            }
            
            
        }
        return "Thanks for choosing our service!";
    }

    public ClientApis renewApi(Apis api, Client client) {
//    	System.out.println(clientApisRepo.findByApiIdAndClientId(apiId, clientId));
//    	return clientApisRepo.findByApiIdAndClientId(apiId, clientId);
    	
    	return clientApisRepo.findByApiAndClient(api,client);
    }

//    public void countApiHit(int apiId, int clientId){
////        ClientApis clientApis = clientApisRepo.findByApiIdAndClientId(apiId, clientId);
//    	
//    	Apis api =  apisService.readApi(apiId);
//    	Client clientFromDb = clientService.readClient(clientId);
//    	
//    	ClientApis clientApis = clientApisRepo.findByApiAndClient(api,clientFromDb);
//
//        Client client = clientApis.getClient();
//        client.setTotalRemainingHits(client.getTotalRemainingHits()-1);
//        client.setTotalUsedHits(client.getTotalUsedHits()+1);
//        clientService.updateClient(client);
//
//        clientApis.setRemainingHitsOfApi(clientApis.getRemainingHitsOfApi()-1);
//        clientApis.setConsumedHitsOfApi(clientApis.getConsumedHitsOfApi()+1);
//
//        clientApisRepo.save(clientApis);
//    }

    public void countApiHitForUser(int apiId, String authorizationHeader) throws VerificationException {
        String bearerAccessToken = authorizationHeader.replace("Bearer ", "");
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
        AccessToken accessToken = verifier.getToken();
        String userName = accessToken.getPreferredUsername();
        
        User user = userRepo.findByUserName(userName);
        Apis api = apisService.readApi(apiId);

        UserApis userApis = userApisRepo.findByApiAndUser(api, user);

        if (userApis != null) {
            if (userApis.getRemainingHitsOfApi() > 0) {
                userApis.setRemainingHitsOfApi(userApis.getRemainingHitsOfApi() - 1);
                userApis.setConsumedHitsOfApi(userApis.getConsumedHitsOfApi() + 1);
                userApisRepo.save(userApis);
            } else {
                throw new IllegalStateException("No remaining hits available for this API.");
            }
        } else {
            throw new IllegalArgumentException("No API subscription found for this user and API");
        }
    }

}
