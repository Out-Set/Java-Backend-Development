package com.practice.apacheCamel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UpdateApiHitCountsRoute extends RouteBuilder {

    static Integer counts = 3;

    @Override
    public void configure() throws Exception {

        // getRemainingHitCount
        from("direct:getRemainingHitCount")
                .process(exchange -> {
                    exchange.setProperty("counts", counts);
                })
                .process(exchange -> {
                    Integer count = exchange.getProperty("counts", Integer.class);
                    System.out.println("Inside getRemainingCount Route, Counts: " + count);

                    if (count <= 0) {
                        // Set Exception
                        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 402);
                        throw new IllegalArgumentException("You have exhausted your API limit, please purchase again to continue your services");
                    }
                })
                .onException(IllegalArgumentException.class)
                .process(exchange -> {
                    // Set Message
                    String finalErrorMessage = "{\"error\": {\"statusCode\": 402, \"errorMessage\": \"" + "You have exhausted your API limit, please purchase again to continue your services" + "\"}}";
                    exchange.getIn().setBody(finalErrorMessage);
                    exchange.getIn().setHeader("Content-Type", "application/json");
                }).handled(true)
                .end();


        // updateCountOnSuccessfulHits
        from("direct:updateCountOnSuccessfulHits")
                .process(exchange -> {
                    int statusCode = exchange.getIn().getHeader("CamelHttpResponseCode", Integer.class);
                    System.out.println("statusCode: " + statusCode);
                    if(String.valueOf(statusCode).startsWith("2")){
                        counts--;
                    }
                });
    }
}
