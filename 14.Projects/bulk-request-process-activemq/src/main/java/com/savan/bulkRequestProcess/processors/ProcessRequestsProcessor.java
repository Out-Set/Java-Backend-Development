package com.savan.bulkRequestProcess.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.bulkRequestProcess.entity.RequestModel;
import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProcessRequestsProcessor extends RouteBuilder {

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    @Override
    public void configure() throws Exception {

        from("direct:processRequests")
                .unmarshal().json(JsonLibrary.Jackson, RequestModel.class)
                .process(exchange -> {
                    RequestModel requestModel = exchange.getIn().getBody(RequestModel.class);
                    log.info("RequestModel in ProcessRequestsProcessor: {}", requestModel.toString());
                    exchange.setProperty("requestModel", requestModel);

                    // Prepare initial audit
                    RequestResponseAudit requestResponseAudit = new RequestResponseAudit();
                    requestResponseAudit.setCorrelationId(requestModel.getCorrelationId());
                    requestResponseAudit.setRequestString(String.valueOf(requestModel.getRequestBody()));
                    requestResponseAudit.setRequestTimeStamp(LocalDateTime.now());
                    requestResponseAudit.setSourceSystem(requestModel.getSourceSystem());
                    requestResponseAudit.setApiName(requestModel.getApiName());
                    requestResponseAudit.setApiUrl(requestModel.getApiUrl());
                    exchange.setProperty("requestResponseAudit", requestResponseAudit);

                    // ------------- Logic to Hit the api-here ---------------- //
                    // Set Request-Body
                    log.info("Request-Body before setting into body: {}", requestModel.getRequestBody());
                    if(requestModel.getRequestBody() == null || requestModel.getRequestBody().toString().isEmpty()){
                        log.info("Null body: {}", requestModel.getRequestBody());
                        String stringReqBody = new ObjectMapper().writeValueAsString(requestModel.getRequestBody());
                        exchange.getIn().setBody(stringReqBody);
                    } else {
                        log.info("Json/List-Json body: {}", requestModel.getRequestBody().toString());
                        exchange.getIn().setBody(requestModel.getRequestBody().toString());
                    }

                    // Set Api-Url and Http-Method Into Exchange-Property
                    exchange.setProperty("finalApiUrl", requestModel.getApiUrl());

                    // Set Http-Method Into Exchange-Property
                    exchange.setProperty("httpMethod", requestModel.getHttpMethod());

                    // Set Headers
                    exchange.getIn().setHeaders(requestModel.getHeaders());

                })
                .doTry()
                    .setHeader(Exchange.HTTP_METHOD, simple("${exchangeProperty.httpMethod}"))
                    .toD("${exchangeProperty.finalApiUrl}?bridgeEndpoint=true")
                    .log("Log just after api call: ${body}")
                .doCatch(Exception.class)
                .process(exchange -> {
                    // Retrieve the exception
                    Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    String httpRespCode = exception.getMessage().substring(exception.getMessage().length() - 4);

                    // Set the Exception-Occurrence and Http-Status-Code into Property
                    exchange.setProperty("httpRespCode", httpRespCode);
                    exchange.setProperty("exceptionOccurred", "YES");
                    exchange.setProperty("exceptionMessage", exception.getMessage());
                })
                .end()
                .log("Request Processed");

    }
}
        /*
        JacksonDataFormat format = new ListJacksonDataFormat(RequestModel.class);
        from("direct:processRequests")
                .unmarshal(format)
                .process(exchange -> {
                    List<RequestModel> requestModelList = (List<RequestModel>)exchange.getIn().getBody(List.class);
                    log.info("RequestModel in ProcessRequestsProcessor: {}", requestModelList.toString());
                    exchange.setProperty("requestModel", requestModelList);

                    // Preparation for Api-Hit
                    for(RequestModel requestModel: requestModelList){
                        // Prepare Initial Audit
                        RequestResponseAudit requestResponseEntity = new RequestResponseAudit();
                        requestResponseEntity.setCorrelationId(requestModel.getCorrelationId());
                        requestResponseEntity.setRequestString(String.valueOf(requestModel.getRequestBody()));
                        requestResponseEntity.setRequestTimeStamp(LocalDateTime.now());
                        requestResponseEntity.setSourceSystem(requestModel.getSourceSystem());
                        requestResponseEntity.setApiName(requestModel.getApiName());
                        requestResponseEntity.setApiUrl(requestModel.getApiUrl());
                        exchange.setProperty("requestResponseEntity", requestResponseEntity);

                        // Prepare and hit api, get response
                        Map<String, String> response = prepareAndHitApis.hitApis(requestModel);

                        // Ensure the response map is not empty and "httpStatusCode" is present
                        if (response != null && response.get("httpStatusCode") != null && response.get("httpStatusCode").startsWith("20")) {
                            log.info("API call resp: {}", response.get("resp"));
                            requestResponseEntity.setResponseString(response.get("resp"));
                            requestResponseEntity.setResponseTimeStamp(LocalDateTime.now());
                            requestResponseEntity.setProcessStatus(true);
                            reqRespEntityService.saveRequests(requestResponseEntity);
                        } else {
                            // Handle the case when the API response doesn't have a valid HTTP status code
                            log.info("API call resp: {}", response != null ? response.get("resp") : "No response received");
                            requestResponseEntity.setResponseString(response != null ? response.get("resp") : "No response received");
                            requestResponseEntity.setResponseTimeStamp(LocalDateTime.now());
                            requestResponseEntity.setProcessStatus(false);
                            reqRespEntityService.saveRequests(requestResponseEntity);
                        }

                    }
                });

         */





// Old-Code
/*
from("direct:processRequests")
                .unmarshal().json(JsonLibrary.Jackson, RequestModel.class)
                .process(exchange -> {
RequestModel requestModel = exchange.getIn().getBody(RequestModel.class);
                    log.info("RequestModel in ProcessRequestsProcessor: {}", requestModel.toString());
        exchange.setProperty("requestModel", requestModel);

// Prepare initial audit
RequestResponseAudit requestResponseEntity = new RequestResponseAudit();
                    requestResponseEntity.setCorrelationId(requestModel.getCorrelationId());
        requestResponseEntity.setRequestString(requestModel.getRequestBody());
        requestResponseEntity.setRequestTimeStamp(LocalDateTime.now());
        requestResponseEntity.setSourceSystem("LOCAL");
                    requestResponseEntity.setApiName(requestModel.getApiName());
        requestResponseEntity.setApiUrl(requestModel.getApiUrl());
        exchange.setProperty("requestResponseEntity", requestResponseEntity);

// Logic to Hit the api-here
                    if(requestModel.getHttpMethod().equals("GET")){
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.GET);
                        log.info("HttpMethod() is set to: {}", "GET");
                    }
                            else if(requestModel.getHttpMethod().equals("POST")){
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.POST);
                        log.info("HttpMethod() is set to: {}", "POST");
                    }
                            else if(requestModel.getHttpMethod().equals("PUT")){
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.PUT);
                        log.info("HttpMethod() is set to: {}", "PUT");
                    }
                            else if(requestModel.getHttpMethod().equals("PATCH")){
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.PATCH);
                        log.info("HttpMethod() is set to: {}", "PATCH");
                    }
                            else if(requestModel.getHttpMethod().equals("DELETE")){
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.DELETE);
                        log.info("HttpMethod() is set to: {}", ";DELETE");
                    }

                            exchange.getIn().setBody(requestModel.getRequestBody());
        exchange.setProperty("finalApiUrl", requestModel.getApiUrl());
        })
        .doTry()
                    .toD("${exchangeProperty.finalApiUrl}?bridgeEndpoint=true")
                .doCatch(Exception.class)
                .process(exchange -> {
// Retrieve the exception
Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
// Set the exception message in the body
                    exchange.getMessage().setBody("API call failed: " + exception.getMessage());
        })
        .end()
                .log("Request Processed");

                /*
                .toD("${exchangeProperty.finalApiUrl}?bridgeEndpoint=true")
                .log("Request Processed");

                 */


/*
                    if (requestModel.getRequestBody() instanceof List) {
                        String stringReqBody = new ObjectMapper().writeValueAsString(requestModel.getRequestBody());
                        log.info("stringReqBody list-type: {}", stringReqBody);
                        exchange.getIn().setBody(stringReqBody);
                    } else if (requestModel.getRequestBody() == null || requestModel.getRequestBody().toString().isEmpty()) {
                        String stringReqBody = new ObjectMapper().writeValueAsString(requestModel.getRequestBody());
                        exchange.getIn().setBody(stringReqBody);
                        log.info("stringReqBody empty: {}", stringReqBody);
                    } else {
                        log.info("stringReqBody normal-type: {}", requestModel.getRequestBody());
                        exchange.getIn().setBody(requestModel.getRequestBody());
                    }

                    log.info("stringReqBody before: {}", requestModel.getRequestBody());
                    String stringReqBody = new ObjectMapper().writeValueAsString(requestModel.getRequestBody());
                    exchange.getIn().setBody(requestModel.getRequestBody());
                    log.info("stringReqBody empty: {}", stringReqBody);

                     */