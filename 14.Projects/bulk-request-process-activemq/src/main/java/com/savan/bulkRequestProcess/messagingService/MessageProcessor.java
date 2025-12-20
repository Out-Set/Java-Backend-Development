package com.savan.bulkRequestProcess.messagingService;

import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.helpers.ActiveMQReader;
import com.savan.bulkRequestProcess.helpers.GetQueueSize;
import com.savan.bulkRequestProcess.service.ReqRespEntityService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
user.request.queue=user-request-queue
process.request.queue=process-request-queue
*/

@Component
public class MessageProcessor extends RouteBuilder {

    @Value("${activemq.read.message.time.interval}")
    private String INTERVAL;

    @Value("${user.request.queue}")
    private String USER_REQUEST_QUEUE;

    @Autowired
    private GetQueueSize getQueueSize;

    @Autowired
    private ActiveMQReader activeMQReader;

    @Autowired
    private ReqRespEntityService reqRespEntityService;

    @Override
    public void configure() throws Exception {

        // Timer end-point will trigger each time on given INTERVAL
        from("timer://queueReadTimer?period="+INTERVAL)
                .process(exchange -> {
                    int QUEUE_SIZE = getQueueSize.getPendingMessagesCount(USER_REQUEST_QUEUE);
                    exchange.setProperty("QUEUE_SIZE", QUEUE_SIZE);
                })
                .choice()
                .when(simple("${exchangeProperty.QUEUE_SIZE} > 0"))
                .process(exchange -> {
                    Integer QUEUE_SIZE = exchange.getProperty("QUEUE_SIZE", Integer.class);
                    System.out.println("USER_REQUEST_QUEUE have - "+QUEUE_SIZE+" messages, started processing ...");
                    activeMQReader.dequeueMessages(USER_REQUEST_QUEUE, QUEUE_SIZE);
                })
                .log("Messages received from USER_REQUEST_QUEUE: [${body}]")
                .endChoice()
                .otherwise()
                .log("USER_REQUEST_QUEUE-Size: ${exchangeProperty.QUEUE_SIZE}")
                .end()
                .log("Bulk-Request-Response, Read-Message-Timer Triggered ... ");


        // Get Messages from 'process-request-queue' and Hit-API
        // If Api-Request is successful save response into database
        // If Api-Request is not successful send back to the 'process-request-queue' with initial body
        from("activemq:queue:process-request-queue?concurrentConsumers=5")
                .errorHandler (
                        deadLetterChannel("activemq:queue:DLQ.process-request-queue")
                                .maximumRedeliveries(3)
                                .redeliveryDelay(5000)
                                .onRedelivery(exchange -> {
                                    // Retrieve the original RequestModel and set it back in the body
                                    String message = exchange.getProperty("message", String.class);
                                    exchange.getIn().setBody(message);
                                })
                                .log("Messages are sent to DLQ.process-request-queue as max retry limit exceeded ...")
                )
                .process(exchange -> {
                    // Before hit API
                    String message = exchange.getIn().getBody(String.class);
                    log.info("Received message from PROCESS_REQUEST_QUEUE: {}", message);

                    // Set message to exchange-property
                    exchange.setProperty("message", message);
                })
                .to("direct:processRequests")
                .process(exchange -> {
                    // Get Response-Body and RequestResponseAudit-Object
                    String response = exchange.getIn().getBody(String.class);
                    RequestResponseAudit requestResponseAudit = exchange.getProperty("requestResponseAudit", RequestResponseAudit.class);

                    // Getting Exception Occurrence
                    String exceptionOccurred = exchange.getProperty("exceptionOccurred", String.class);
                    String exceptionMessage = exchange.getProperty("exceptionMessage", String.class);
                    // Throwing failure Exception (In case of Exception), and Persist-Current-Response
                    if (exceptionOccurred != null && exceptionOccurred.equals("YES")) {
                        // Failed Response Audit
                        requestResponseAudit.setResponseString(exceptionMessage);
                        requestResponseAudit.setResponseTimeStamp(LocalDateTime.now());
                        requestResponseAudit.setProcessStatus(false);
                        reqRespEntityService.saveRequests(requestResponseAudit);

                        System.out.println("Exception occurred: " + exceptionOccurred + ", retrying ...");
                        System.out.println("Exception message: " + exceptionMessage);
                        throw new RuntimeException("Processing Failed!");
                    } else {
                        // Success Response Audit
                        requestResponseAudit.setResponseString(response);
                        requestResponseAudit.setResponseTimeStamp(LocalDateTime.now());
                        requestResponseAudit.setProcessStatus(true);
                        reqRespEntityService.saveRequests(requestResponseAudit);

                        System.out.println("Message processed successfully without any exception: " + response);
                    }
                })
                .onException(Exception.class)
                .handled(false)
                .end()
                .log("Received message: ${body}")
                .to("activemq:queue:final-response-queue?exchangePattern=InOnly");


        // Get Message from
        from("activemq:queue:final-response-queue")
                .log("Final Response: ${body}");

    }

}

/*

// Get Messages from 'user-request-queue' and Prepare-Requests then send it to 'process-request-queue'
        from("activemq:queue:user-request-queue")
                .to("direct:prepareRequests")
                .to("activemq:queue:process-request-queue?exchangePattern=InOnly")
                .log("Sent to process-request-queue");



        // Get Messages from 'process-request-queue' and Hit-API
        // If Api-Request is successful save response into database
        // If Api-Request is not successful send back to the 'process-request-queue' with initial body
        from("activemq:queue:process-request-queue")
                .to("direct:processRequests")
                .choice()
                    .when(header("CamelHttpResponseCode").isEqualTo(200))
                        .to("direct:saveSuccessResponses")
                    .otherwise()
                        .to("direct:saveFailedResponses")
                    .end()
                .log("Received message: ${body}");

                /*
                .to("direct:saveSuccessResponses")
                .to("direct:saveFailedResponses")
                .log("Received message: ${body}");
*/


// Just old code
/*
// Get Messages from 'user-request-queue' and Prepare-Requests then send it to 'process-request-queue'
        from("activemq:queue:user-request-queue")
                .aggregate(constant(true), new GroupedExchangeAggregationStrategy()) // Aggregate messages into a list
                .completionSize(10) // Batch size of 10
                //.completionTimeout(15000)  // Uncomment for a timeout to process incomplete batches
                .log("Received batch of messages from user-request-queue: ${body}")
                .process(exchange -> {
                    // Retrieve the list of exchanges (batch of messages)
                    List<Exchange> exchanges = exchange.getIn().getBody(List.class);

                    // Convert each message into RequestModel and collect them
                    List<RequestModel> requestModels = exchanges.stream()
                            .map(ex -> {
                                String messageBody = ex.getIn().getBody(String.class);
                                System.out.println("Processing message: " + messageBody);
                                return convertToRequestModel(messageBody);
                            })
                            .collect(Collectors.toList());

                    exchange.getIn().setBody(requestModels);
                })
                .marshal().json()
                .to("direct:prepareRequests")
                .split(body())
                .marshal().json()
                .to("activemq:queue:process-request-queue?exchangePattern=InOnly")
                .log("Sent to process-request-queue");
 */