package com.spring.security.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Routes extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Route - 0
        rest("/demo/route")
                .get()
                .to("direct:demoRoute");

        from("direct:demoRoute")
                .process(exchange -> {
                    exchange.getMessage().setBody("In demo route ....");
                })
                .log("Body :: ${body}");

        // Route - 1
        rest("/camelApi/verifyPAN")
                .post()
                .to("direct:callPanAPI");

        from("direct:callPanAPI")
                // Note: Remove the Authorization Header before calling other api(not needed here)
                .setHeader("Authorization", constant("no auth"))
                .to("http://194.233.84.104:8081/BitsFlow-App/rest/pan/verification?bridgeEndpoint=true")
                .log("Body :: ${body}");


        // Route - 2
        rest("/camelApi/product/all")
                .get()
                .to("direct:callAPI");

        from("direct:callAPI")
                // Note: Do not remove the Authorization Header before calling other api(needed here)
                // Must care about the token and roles
                .to("http://localhost:8080/product/all?bridgeEndpoint=true")
                .log("Body :: ${body}");


        // Route - 3
        from("servlet:camelApi/getAllProducts")
                // Note: Do not remove the Authorization Header before calling other api(needed here)
                // Must care about the token and roles
                .to("http://localhost:8080/product/all?bridgeEndpoint=true")
                .log("Body :: ${body}");
    }
}

// Post-man
// 1. http://localhost:8080/camel/camelApi/verifyPAN with Token
// 2. http://localhost:8080/camel/camelApi/product/all with Token
// 3. http://localhost:8080/camel/camelApi/getAllProducts with Token