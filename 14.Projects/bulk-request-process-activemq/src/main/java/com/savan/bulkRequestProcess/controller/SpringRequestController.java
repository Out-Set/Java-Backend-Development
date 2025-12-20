package com.savan.bulkRequestProcess.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.bulkRequestProcess.messagingService.SendToQueue;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SpringRequestController {

    @Autowired
    private SendToQueue sendToQueue;

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    @PostMapping("/send/request")
    public String sendRequest(@RequestBody String message) throws JsonProcessingException {
        // Send message to queue
        Map<String, String> mpp = new HashMap<>();
        String correlationId = UUID.randomUUID().toString();
        mpp.put("correlationId", correlationId);
        mpp.put("data", message);
        mpp.put("apiName", "ADD-TWO-NUMS");

        // Convert the final gstAuthRequest to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyJson = objectMapper.writeValueAsString(mpp);

        sendToQueue.toUserRequestQueue(requestBodyJson);

        return "Request generated with correlation-id: "+correlationId;
    }
}
