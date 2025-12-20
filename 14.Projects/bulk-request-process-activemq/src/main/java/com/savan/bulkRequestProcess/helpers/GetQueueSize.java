package com.savan.bulkRequestProcess.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetQueueSize {

    @Value("${spring.activemq.user}")
    private String USERNAME;

    @Value("${spring.activemq.password}")
    private String PASSWORD;

    @Value("${spring.activemq.brokerName}")
    private String BROKER_NAME;

    @Value("${spring.activemq.rest-endpoint-base-url}")
    private String BASE_URL;

    public int getPendingMessagesCount(String QUEUE_NAME) {

        String url = BASE_URL+"/api/jolokia/read/org.apache.activemq:type=Broker,brokerName="+BROKER_NAME+",destinationType=Queue,destinationName="+QUEUE_NAME;

        RestTemplate restTemplate = new RestTemplate();

        // Create headers and set Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(USERNAME, PASSWORD);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            // Extract QueueSize
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("value").path("QueueSize").asInt();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
