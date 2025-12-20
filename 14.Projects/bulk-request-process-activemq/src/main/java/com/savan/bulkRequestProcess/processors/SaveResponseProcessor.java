package com.savan.bulkRequestProcess.processors;

import com.savan.bulkRequestProcess.entity.RequestModel;
import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SaveResponseProcessor extends RouteBuilder {

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    @Override
    public void configure() throws Exception {

        // Audit Success Response
        from("direct:saveSuccessResponses")
                .process(exchange -> {
                    String response = exchange.getIn().getBody(String.class);
                    log.info("API call resp: {}", response);
                    RequestModel requestModel = exchange.getProperty("requestModel", RequestModel.class);

                    RequestResponseAudit requestResponseAudit = exchange.getProperty("requestResponseAudit", RequestResponseAudit.class);
                    requestResponseAudit.setResponseString(response);
                    requestResponseAudit.setResponseTimeStamp(LocalDateTime.now());
                    requestResponseAudit.setProcessStatus(true);
                    reqRespEntityService.saveRequests(requestResponseAudit);
                })
                .log("Response saved");

        // Audit Failed Response
        from("direct:saveFailedResponses")
                .process(exchange -> {
                    String response = exchange.getIn().getBody(String.class);
                    log.info("API call resp: {}", response);
                    RequestModel requestModel = exchange.getProperty("requestModel", RequestModel.class);

                    RequestResponseAudit requestResponseAudit = exchange.getProperty("requestResponseAudit", RequestResponseAudit.class);
                    requestResponseAudit.setResponseString(response);
                    requestResponseAudit.setResponseTimeStamp(LocalDateTime.now());
                    requestResponseAudit.setProcessStatus(false);
                    reqRespEntityService.saveRequests(requestResponseAudit);

                    // Sending back the request to queue
                    // System.out.println("Sending request back to the queue with correlation-Id :: "+requestModel.getCorrelationId());
                })
                .log("Response Not saved");
    }
}
