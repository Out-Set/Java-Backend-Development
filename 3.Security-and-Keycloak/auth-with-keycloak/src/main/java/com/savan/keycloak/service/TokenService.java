package com.savan.keycloak.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${keycloak.accessToken.url}")
    private String accessTokenUrl;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${keycloak.accessToken.url}")
    // private String accessTokenUrl;

    public ResponseEntity<String> getAccessToken(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = String.format("username=%s&password=%s&grant_type=%s&client_id=%s&client_secret=%s", username, password, "password", clientId, clientSecret);
        // log.info("Access token requestBody: {}", requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUrl, entity, String.class);
            // log.info("response from getToken: {}", response);
            return response;
        } catch (Exception e) {
            // log.info("exception occurred while generating token: {}", e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseJson = mapper.createObjectNode();
            responseJson.put("status", "400");
            responseJson.put("message", "No active account found with the given credentials");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }
}
