package com.savan.bulkRequestProcess.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.bulkRequestProcess.entity.RequestModel;
import com.savan.bulkRequestProcess.helpers.ActiveMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PrepareRequestProcessor {

    @Value("${process.request.queue}")
    private String PROCESS_REQUEST_QUEUE;

    @Value("${activemq.apisList.bulkRequestsType}")
    private List<String> BULK_REUESTTYPE_APISLIST;

    @Autowired
    private ActiveMQSender activeMQSender;

    // Read messages and Prepare-Request-Model ( Used inside JMS-Session )
    public void readMessagesAndPrepareRequestModel(List<String> messages){

        // Convert each message into RequestModel and collect them
        List<RequestModel> requestModels = messages.stream()
                .map(this::convertToRequestModel)
                .collect(Collectors.toList());
        System.out.println("requestModels: "+requestModels);

        // Filter based on bulk/single request-type apis
        List<RequestModel> finalRequestModels = processRequestModels(requestModels, BULK_REUESTTYPE_APISLIST);

        // Send messages to PROCESS_REQUEST_QUEUE
        finalRequestModels.forEach(requestModel ->{
            activeMQSender.sendMessageToQueue(PROCESS_REQUEST_QUEUE, convertToRequestModelJson(requestModel));
            System.out.println("Message sent to: "+PROCESS_REQUEST_QUEUE + ", message: "+requestModel);
        });
    }

    // Filter requests for APIs that accept list-type request bodies and prepare Final List<RequestModel>
    public List<RequestModel> processRequestModels(List<RequestModel> requestModels, List<String> bulkRequestsApis) {

        // Filter requests for APIs that accept list-type request bodies
        Map<String, List<Object>> listReqBody = new HashMap<>();
        Map<String, RequestModel> listReqBodyApisDetails = new HashMap<>();
        for(RequestModel requestModel: requestModels){
            if(bulkRequestsApis.contains(requestModel.getApiName())){
                listReqBody
                        .computeIfAbsent(requestModel.getApiName(), k -> new ArrayList<>())
                        .add(requestModel.getRequestBody());

                listReqBodyApisDetails.put(requestModel.getApiName(), requestModel);
            }
        }

        // Prepare-Final-RequestModel
        List<RequestModel> finalRequestModels = new ArrayList<>();
        Set<String> listReqBodyApis = new HashSet<>();
        for(RequestModel requestModel: requestModels){
            if(bulkRequestsApis.contains(requestModel.getApiName())){
                if(!listReqBodyApis.contains(requestModel.getApiName())) {
                    RequestModel newRequestModel = new RequestModel();
                    newRequestModel.setCorrelationId(listReqBodyApisDetails.get(requestModel.getApiName()).getCorrelationId());
                    newRequestModel.setSourceSystem(listReqBodyApisDetails.get(requestModel.getApiName()).getSourceSystem());
                    newRequestModel.setApiUrl(listReqBodyApisDetails.get(requestModel.getApiName()).getApiUrl());
                    newRequestModel.setApiName(listReqBodyApisDetails.get(requestModel.getApiName()).getApiName());
                    newRequestModel.setHeaders(listReqBodyApisDetails.get(requestModel.getApiName()).getHeaders());
                    newRequestModel.setHttpMethod(listReqBodyApisDetails.get(requestModel.getApiName()).getHttpMethod());
                    newRequestModel.setContentType(listReqBodyApisDetails.get(requestModel.getApiName()).getContentType());

                    newRequestModel.setRequestBody(listReqBody.get(requestModel.getApiName()));
                    finalRequestModels.add(newRequestModel);

                    // Add to Set, so that it won't be added next time
                    listReqBodyApis.add(requestModel.getApiName());
                }
            } else {
                finalRequestModels.add(requestModel);
            }
        }

        return finalRequestModels;
    }


    // Convert Json-Message into RequestModel
    private RequestModel convertToRequestModel(String messageBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(messageBody, RequestModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse message: " + messageBody, e);
        }
    }

    // Convert RequestModel into Json-Message
    private static String convertToRequestModelJson(RequestModel requestModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(requestModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse requestModel: " + requestModel, e);
        }
    }
}
