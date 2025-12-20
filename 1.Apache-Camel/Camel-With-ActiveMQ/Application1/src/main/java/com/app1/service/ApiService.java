package com.app1.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private static final String API_URL = "http://localhost:8094/userDtl";

    public ResponseEntity<String> fetchDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.exchange(API_URL, HttpMethod.GET, null, String.class);
        } catch (Exception e) {
            System.out.println("Retrying in 10 sec, Please wait ... ");
            return ResponseEntity.badRequest().body("API request failed");
        }
    }

    public Object fetchApiDataWithRetry(ApiService apiService) {
        int maxRetries = 5;
        int retryCount = 0;

        while (retryCount < maxRetries) {

            retryCount++;

            try {
                ResponseEntity<String> response = apiService.fetchDataFromApi();

                if (response.getStatusCode().is2xxSuccessful()) {
//                    System.out.println("Got the Response: " + response);
                    return response.getBody();
                }

                Thread.sleep(10000);
            } catch (InterruptedException e){
                System.out.println("Retrying in 10 sec, Please wait ... ");
            }
        }
        System.out.println("Maximum retrying count reached, Please try again later. Thanks!");
        ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("API request timed out.");
        return "API request timed out. Please try again later.";
    }
}




