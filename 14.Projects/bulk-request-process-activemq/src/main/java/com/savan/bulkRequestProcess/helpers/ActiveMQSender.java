package com.savan.bulkRequestProcess.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Component
public class ActiveMQSender {

    @Value("${spring.activemq.user}")
    private String USERNAME;

    @Value("${spring.activemq.password}")
    private String PASSWORD;

    @Value("${spring.activemq.rest-endpoint-base-url}")
    private String BASE_URL;

    public void sendMessageToQueue(String QUEUE_NAME, String message) {

        String finalUrl = BASE_URL+"/api/message/"+QUEUE_NAME+"?type=queue";

        RestTemplate restTemplate = new RestTemplate();

        // Set Basic Authentication Header
        String auth = USERNAME + ":" + PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set request body
        String requestBody = "body=" + message;
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(
                finalUrl, HttpMethod.POST, request, String.class
        );

        // Print response
        System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody() + " - " + QUEUE_NAME);
    }
}
