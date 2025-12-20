package com.savan.config.cors.controller;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelController extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Get api
        rest("/config/cors/camel").get()
                .to("direct:corsConfigGet");

        from("direct:corsConfigGet")
                .process(exchange -> {
                    String message = "Get api from camel !!";
                    exchange.getMessage().setBody(message);
                });


        // Post api
        rest("/config/cors/camel").post()
                .consumes("application/json")
                .to("direct:corsConfigPost");

        from("direct:corsConfigPost")
                .process(exchange -> {
                    String message = "Post api from camel with request: "+exchange.getMessage().getBody(String.class);
                    exchange.getMessage().setBody(message);
                });
    }
}
