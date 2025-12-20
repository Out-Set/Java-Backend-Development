package com.savan.bulkRequestProcess.processors;

import com.savan.bulkRequestProcess.entity.RequestModel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ExtractHeadersAndBodyProcessor extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Extract Headers and Body
        from("direct:extractHeadersAndBody")
                .process(exchange -> {

                    String correlationId = UUID.randomUUID().toString();
                    String apiName = exchange.getProperty("API-NAME", String.class);
                    String apiUrl = exchange.getProperty("finalApiUrl", String.class);

                    // Map<String, Object> headers = exchange.getMessage().getHeaders(); // Don't use it, creates issue in json-conversion
                    Map<String, Object> headers = new HashMap<>();
                    try{
                        headers = exchange.getProperty("headers", Map.class);
                    } catch (Exception e){
                        log.info("No Initial Headers to set");
                    }

                    String requestBody = exchange.getIn().getBody(String.class);
                    String httpMethod = exchange.getMessage().getHeader("CamelHttpMethod", String.class);
                    String contentType = exchange.getMessage().getHeader("Content-Type", String.class);
                    headers.put("Content-Type", contentType);

                    String sourceSystem = String.valueOf(headers.get("SOURCE_SYSTEM"));

                    // Prepare RequestModel Object
                    RequestModel requestModel = new RequestModel(correlationId, sourceSystem, apiUrl, apiName, headers, requestBody, httpMethod, contentType);
                    log.info("RequestModel in extractHeadersAndBody: {}", requestModel);

                    exchange.getIn().setBody(requestModel);

                    exchange.setProperty("correlationId", correlationId);
                })
                .marshal().json();
    }
}

