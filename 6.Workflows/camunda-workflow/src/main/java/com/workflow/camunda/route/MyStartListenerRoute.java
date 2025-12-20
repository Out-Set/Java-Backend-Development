package com.workflow.camunda.route;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyStartListenerRoute extends RouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configure() throws Exception {

        from("direct:myStartListenerRoute")
                .id("LEAVE_MANAGEMENT")
                .process(exchange -> {
                    logger.info("Process started: {}", exchange.getFromRouteId());
                });
    }
}
