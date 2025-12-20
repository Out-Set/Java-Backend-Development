package com.savan.bulkRequestProcess.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.entity.ResponseDto;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GetResponseController {

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    // Check if Request is processed or not
    // If processed return the response else the process-status
    @GetMapping("/getResp")
    public ResponseDto demoFun(@RequestParam String correlationId) throws JsonProcessingException {
        RequestResponseAudit entity = reqRespEntityService.findByCorrelationId(correlationId);
        System.out.println("ResponseString: "+entity.getResponseString());

        ResponseDto responseDto = new ResponseDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseString = entity.getResponseString();
        Object parsedResponse;

        // Determine the type of the response string
        try {
            JsonNode jsonNode = objectMapper.readTree(responseString);
            if (jsonNode.isArray()) {
                // Parse as JSON array
                parsedResponse = objectMapper.readValue(responseString, new TypeReference<List<Map<String, Object>>>() {});
            } else if (jsonNode.isObject()) {
                // Parse as JSON object
                parsedResponse = objectMapper.readValue(responseString, new TypeReference<Map<String, Object>>() {});
            } else {
                // Handle as plain string
                parsedResponse = responseString;
            }
        } catch (Exception e) {
            // If parsing fails, treat it as a plain string
            parsedResponse = responseString;
        }

        if(entity.getProcessStatus()){
            responseDto.setMessage("Request processed successfully");
            responseDto.setCorrelationId(correlationId);
            responseDto.setStatus("processed");
            responseDto.setResponse(parsedResponse);
            return responseDto;
        } else {
            responseDto.setMessage("Request not processed");
            responseDto.setCorrelationId(correlationId);
            responseDto.setStatus("failed");
            responseDto.setResponse(parsedResponse);
            return responseDto;
        }
    }
}
