package com.savan.bulkRequestProcess.controller;

import com.savan.bulkRequestProcess.entity.ResponseDto;
import com.savan.bulkRequestProcess.helpers.ActiveMQSender;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
user.request.queue=user-request-queue
process.request.queue=process-request-queue

sumOf.nNums.api.url = http://localhost:8082/demo/sum
devide.mByn.api.url = http://localhost:8082/demo/devide
greet.message.api.url = http://localhost:8082/BitsFlow-App/demo/message
pan.verification.api.url = https://integration-container-qa01.apps.finfinity.co/BitsFlow-App/rest/pan/verification
process.list.json = http://localhost:8082/demo/processListJson
return.list.json = http://localhost:8082/demo/returnJson
*/

@Component
public class CamelRequestController extends RouteBuilder {

    @Value("${sumOf.nNums.api.url}")
    private String sumOfNNums;

    @Value("${devide.mByn.api.url}")
    private String devideMbyN;

    @Value("${greet.message.api.url}")
    private String greetMessage;

    @Value("${pan.verification.api.url}")
    private String panVerify;

    @Value("${process.list.json}")
    private String processListJson;

    @Value("${return.list.json}")
    private String returnListJson;

    @Autowired
    private ActiveMQSender activeMQSender;

    @Override
    public void configure() throws Exception {

        // Send the request to 'user-request-queue'
        rest("/pan")
                .post()
                .consumes("application/json")
                .to("direct:verifyPan");

        from("direct:verifyPan")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    String TRANSACTION_REFERENCE_ID = exchange.getIn().getHeader("TRANSACTION_REFERENCE_ID", String.class);
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("TRANSACTION_REFERENCE_ID", TRANSACTION_REFERENCE_ID);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "VERIFY-PAN");
                    exchange.setProperty("finalApiUrl", panVerify);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");


        // Send the request to 'user-request-queue'
        rest("/message")
                .post()
                .consumes("application/json")
                .to("direct:bulkMessageRoute");

        from("direct:bulkMessageRoute")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "SEND-BULK-MESSAGE");
                    exchange.setProperty("finalApiUrl", greetMessage);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");


        // Send the request to 'user-request-queue'
        rest("/sumOfN")
                .get()
                .to("direct:sumOfNNumbers");

        from("direct:sumOfNNumbers")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    Integer N = exchange.getIn().getHeader("N", Integer.class);
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("N", N);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "SUM-OF-N-NUMS");
                    String finalApiUrl = sumOfNNums+"/"+N;
                    exchange.setProperty("finalApiUrl", finalApiUrl);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");


        // Send the request to 'user-request-queue'
        rest("/devideMByN")
                .get()
                .to("direct:devideMbyN");

        from("direct:devideMbyN")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    Integer M = exchange.getIn().getHeader("M", Integer.class);
                    Integer N = exchange.getIn().getHeader("N", Integer.class);
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("M", M);
                    headers.put("N", N);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "DEVIDE-M-BY-N");
                    String finalApiUrl = devideMbyN+"/"+M+"/"+N;
                    exchange.setProperty("finalApiUrl", finalApiUrl);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");


        // Send the request to 'user-request-queue'
        rest("/returnListJson")
                .get()
                .to("direct:listJsonReturn");

        from("direct:listJsonReturn")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    Integer N = exchange.getIn().getHeader("N", Integer.class);
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("N", N);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "RETURN-LIST-JSON");
                    String finalApiUrl = returnListJson+"/"+N;
                    exchange.setProperty("finalApiUrl", finalApiUrl);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");


        // Send the request to 'user-request-queue'
        rest("/processListJson")
                .post()
                .to("direct:listJsonProcess");

        from("direct:listJsonProcess")
                .process(exchange -> {

                    Map<String, Object> headers = new HashMap<>();
                    String sourceSystem = exchange.getIn().getHeader("SOURCE_SYSTEM", String.class);
                    headers.put("SOURCE_SYSTEM", sourceSystem);
                    exchange.setProperty("headers", headers);

                    exchange.setProperty("API-NAME", "PROCESS-LIST-JSON");
                    String finalApiUrl = processListJson;
                    exchange.setProperty("finalApiUrl", finalApiUrl);
                })
                .to("direct:extractHeadersAndBody")
                // .to("activemq:queue:user-request-queue?exchangePattern=InOnly")
                .process(exchange -> {
                    activeMQSender.sendMessageToQueue("user-request-queue", exchange.getIn().getBody(String.class));
                    String correlationId = exchange.getProperty("correlationId", String.class);
                    ResponseDto initAck = new ResponseDto("Request initiated successfully", correlationId, "queued", null);
                    exchange.getIn().setBody(initAck);
                })
                .marshal().json()
                .log("Request sent to user-request-queue");

    }
}
