package com.savan.bulkRequestProcess.messagingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/*
user.request.queue=user-request-queue
process.request.queue=process-request-queue
*/

@Slf4j
@Component
public class ReceiveFromQueue {

    @Value("${user.request.queue}")
    private String userRequestQueue;

    @Value("${process.request.queue}")
    private String processRequestQueue;

    @Autowired
    private SendToQueue sendToQueue;

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    // @JmsListener(destination = "${my.app.desti-name}")
    public void readData(String data) {
        // System.out.println("Data Consumed from queue :: " + data);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert object to Map
            Map<String, Object> mapData = objectMapper.readValue(data, Map.class);
            // log.info("Data from queue :: "+mapData);
            if (mapData != null) {
                String correlationId = (String) mapData.get("correlationId");
                String message = (String) mapData.get("data");
                log.info("correlationId: {} and message: {}", correlationId, message);

                RequestResponseAudit requestResponseAudit = new RequestResponseAudit();
                requestResponseAudit.setCorrelationId(correlationId);
                requestResponseAudit.setRequestString(message);
                requestResponseAudit.setRequestTimeStamp(LocalDateTime.now());
                requestResponseAudit.setSourceSystem("LOCAL");

                try {
                    // Processing Requests
                    System.out.println("Started Processing Requests for correlation-Id :: "+correlationId);
                    requestResponseAudit.setResponseString("Hii request is processed having correlation-id - "+correlationId);
                    // requestResponseAudit.setResponseString(String.valueOf(10/0));
                    requestResponseAudit.setResponseTimeStamp(LocalDateTime.now());
                    requestResponseAudit.setProcessStatus(true);
                    reqRespEntityService.saveRequests(requestResponseAudit);
                    System.out.println("Requests Processed for correlation-Id :: "+correlationId);
                } catch (Exception e) {
                    System.out.println("Processing Request failed for correlation-Id :: "+correlationId+", with message - "+e.getMessage());
                    requestResponseAudit.setProcessStatus(false);
                    reqRespEntityService.saveRequests(requestResponseAudit);

                    // Sending back the request to queue
                    System.out.println("Sending request back to the queue with correlation-Id :: "+correlationId);
                    // sendToQueue.sendData(objectMapper.writeValueAsString(mapData));
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred! with message: "+e.getMessage());
        }
    }
}

