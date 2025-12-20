package com.security.keycloak.controllers;

import java.util.List;
import java.util.Map;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.entity.Client;
import com.security.keycloak.entity.ClientApis;
import com.security.keycloak.service.ApisService;
import com.security.keycloak.service.BuyApiService;
import com.security.keycloak.service.ClientService;

@RestController
@CrossOrigin("*")
public class BuyApiController {

    @Autowired
    private BuyApiService buyApiService;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ApisService apisService;
    
    @PostMapping("/buy")
    public String buyApis(@RequestBody Map<String, Object> request) throws VerificationException {
    	System.out.println("BuyApiController.buyApis()");
        //int clientId = (int) request.get("clientId");
       // System.out.println("Client id :: "+clientId);

    	//steps to fetch userId and username from access token passed with the request
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(request.get("accessToken").toString(), AccessToken.class);
        AccessToken accessToken = verifier.getToken();
        String userId = accessToken.getSubject();
        String userName = accessToken.getPreferredUsername(); //fecthing username from access token
        
        List<Map<String, Object>> apisWithNoOfHits = (List<Map<String, Object>>) request.get("apisWithNoOfHits");
        try {
        	 //return buyApiService.buyApis(clientId, apisWithNoOfHits, userId, userName);
        	 return buyApiService.buyApis(apisWithNoOfHits, userId, userName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @GetMapping("/renew")
    public ClientApis renewApi(@RequestBody Map<String, Integer> request) {
    	
    	int apiId = request.get("apiId");
    	int clientId = request.get("clientId");
    	System.out.println("apiId: " + apiId + " clientId: " + clientId);
//    	buyApiService.renewApi(apiId, clientId);
    	Client client = clientService.readClient(clientId);
    	Apis api =  apisService.readApi(apiId);
    	return buyApiService.renewApi(api, client);
    }

//    @GetMapping("/countApiHit/{apiId}/{userId}")
//    public String getPanDtl(@PathVariable int apiId, @PathVariable int userId){
//        buyApiService.countApiHit(apiId, userId);
//        return "Hi, You just used 1-hit.";
//    }
    
    @GetMapping("/countApiHit/{apiId}")
    public ResponseEntity<String> countUserApiHit(@PathVariable int apiId, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            buyApiService.countApiHitForUser(apiId, authorizationHeader);
            return ResponseEntity.ok("Hi, You just used 1-hit.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    
//    @GetMapping("/getAllApis")
//    public String getAllApis(@)
}
