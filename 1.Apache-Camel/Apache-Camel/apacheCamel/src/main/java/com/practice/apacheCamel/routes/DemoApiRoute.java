package com.practice.apacheCamel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.practice.apacheCamel.routes.UpdateApiHitCountsRoute.counts;

@Component
public class DemoApiRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("/demoRoute/getData")
                .get()
                .to("direct:products");

        from("direct:products")
                // verifyApiService
                .to("direct:verifyApiService")
                // getRemainingHitCount
                .to("direct:getRemainingHitCount")
                // Hit Api-Service
                .to("http://localhost:9081/demo/getData?bridgeEndpoint=true")
                .to("direct:updateCountOnSuccessfulHits")
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(body + ": Remaining counts: " + counts);
                })
                .log("${body}");

    }
}
