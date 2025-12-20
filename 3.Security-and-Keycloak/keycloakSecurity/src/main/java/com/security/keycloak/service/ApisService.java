package com.security.keycloak.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.entity.User;
import com.security.keycloak.entity.UserApis;
import com.security.keycloak.repository.ApisRepo;
import com.security.keycloak.repository.UserApisRepo;
import com.security.keycloak.repository.UserRepo;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApisService {

    @Autowired
    private ApisRepo apisRepo;
    
    @Autowired
    private UserApisRepo userApisRepo;
    
    @Autowired
    private UserRepo userRepo;

    public String createApi(Apis apis){
        apisRepo.save(apis);
        return "Api added successfully!";
    }

    public String createManyApis(List<Apis> apis){
        apisRepo.saveAll(apis);
        return "Apis are added successfully!";
    }

	public List<Apis> getAllApi() {
		
		return apisRepo.findAll();
	}
    
    public Apis readApi(int id){
        return apisRepo.findById(id).get();
    }
    
    public Object getAllApiDetails(String authorizationHeader) throws VerificationException {
    	String bearerAccessToken = authorizationHeader.replace("Bearer ", "");
    	TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
        AccessToken accessToken = verifier.getToken();
        String userName = accessToken.getPreferredUsername();
       // userApisRepo.fin
        User user = userRepo.findByUserName(userName);
    	List<UserApis> userApis = userApisRepo.findByUser(user);
    	List<Object> responseData = new LinkedList<Object>();
    	Map<String, Object> dbData;
    	
    	for(UserApis c : userApis) {
			dbData = new LinkedHashMap<String, Object>();
			dbData.put("id",c.getId());
			dbData.put("api_id", c.getApi().getId());
			dbData.put("apiName",c.getApi().getApiName());
			dbData.put("consumedHitsOfApi", c.getConsumedHitsOfApi());
			dbData.put("remainingHitsOfApi", c.getRemainingHitsOfApi());
			
			responseData.add(dbData);
		}
    	Collections.sort(responseData, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Integer apiId1 = (Integer) ((Map<String, Object>) o1).get("api_id");
                Integer apiId2 = (Integer) ((Map<String, Object>) o2).get("api_id");
                return apiId1.compareTo(apiId2);
            }
        });
		return responseData;
    }

    public String updateApi(Apis apis){
        Apis existingApi = apisRepo.findById(apis.getId()).get();
        existingApi.setApiName(apis.getApiName());
        existingApi.setDescription(apis.getDescription());
        existingApi.setServiceType(apis.getServiceType());
        existingApi.setPerHitPrice(apis.getPerHitPrice());
        existingApi.setClientApisList(apis.getClientApisList());
        apisRepo.save(existingApi);
        return "Api details updated successfully!";
    }

    public String deleteApi(int id){
        apisRepo.deleteById(id);
        return "Api deleted successfully!";
    }
}
