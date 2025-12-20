package com.practice.apacheCamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerifyApiServiceRoute extends RouteBuilder {

    List<String> services = List.of("ADD_VERIFICATION", "PAN_VERIFICATION");

    @Override
    public void configure() throws Exception {

        // verifyApiService
        from("direct:verifyApiService")
                .process(exchange -> {
                    String service = exchange.getIn().getHeader("SERVICE", String.class);
                    if (service != null) {
                        boolean isAvailable = services.contains(service);
                        exchange.setProperty("isAvailable", isAvailable ? "available" : "unavailable");
                    }
                })
                .process(exchange -> {
                    String isAvailable = exchange.getProperty("isAvailable", String.class);
                    System.out.println("Inside verifyService Route, Counts: " + isAvailable);

                    if (!isAvailable.equals("available")) {
                        // Set Exception
                        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 403);
                        exchange.setException(new IllegalArgumentException("Not Allowed, Please visit www.bitsflowtechnologies.com and buy the services"));
                    }
                })
                .onException(IllegalArgumentException.class)
                .process(exchange -> {
                    // Set Message
                    String finalErrorMessage = "{\"error\": {\"statusCode\": 403, \"errorMessage\": \"" + "Not Allowed, Please visit www.bitsflowtechnologies.com and buy the services" + "\"}}";
                    exchange.getIn().setBody(finalErrorMessage);
                    exchange.getIn().setHeader("Content-Type", "application/json");
                }).handled(true)
                .end();
    }
}
