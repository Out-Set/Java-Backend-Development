package com.savan.keycloak.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonHelper {

    @Autowired
    private RestTemplateService restTemplateService;

    // Get Access-token
    public String getAccessToken(){
        String response = restTemplateService.getAccessToken("savan", "12345").getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        if(response != null) {
            try {
                Map<String, String> dataMap = objectMapper.readValue(response, Map.class);
                return dataMap.get("access_token");
            } catch (Exception e) {
                return "An error occurred!";
            }
        }
        return "Response in null!";
    }

}