package com.savan.keycloak.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Service
public class RestTemplateService {

    @Value("${keycloak.admin.grant-type}")
    private String grantType;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${keycloak.accessToken.url}")
    private String accessTokenUrl;

    @Autowired
    private RestTemplate restTemplate;

    // Common Rest Apis Method
    public ResponseEntity<String> hitKeycloakApi(String url, HttpMethod httpMethod, Object requestPayload, String accessToken, Map<String, Object> queryParams) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        String finalUrl = uriBuilder.toUriString();
        System.out.println("Final URL: " + finalUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = (requestPayload != null) ? new HttpEntity<>(requestPayload, headers) : new HttpEntity<>(headers);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(finalUrl, httpMethod, entity, String.class);
        } catch (HttpClientErrorException e) {
            String errorResponse = e.getResponseBodyAsString();
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorResponse);
        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode errorJson = objectMapper.createObjectNode()
                    .put("status", "500")
                    .put("message", "Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJson.toString());
        }
        return response;
    }

    // Get Access-Token
    public ResponseEntity<String> getAccessToken(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = String.format("username=%s&password=%s&grant_type=%s&client_id=%s&client_secret=%s", username, password, grantType, clientId, clientSecret);
        System.out.println("Access token requestBody: "+requestBody);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUrl, entity, String.class);
            System.out.println("response from getToken: "+ response);
            return response;
        } catch (Exception e) {
            System.out.println("exception occurred while generating token: "+ e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseJson = mapper.createObjectNode();
            responseJson.put("status", "400");
            responseJson.put("message", "No active account found with the given credentials");
            return ResponseEntity.status(HttpStatus.OK).body(responseJson.toString());
        }
    }
}
