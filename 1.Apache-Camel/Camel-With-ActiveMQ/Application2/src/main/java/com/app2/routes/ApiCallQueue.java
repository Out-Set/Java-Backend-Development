package com.app2.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApiCallQueue extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue-10").log("From Queue-10: ${body}");
    }
}
