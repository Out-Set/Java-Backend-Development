package com.savan.quartz.scheduler.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DemoCamelQuartzProcessor extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("quartz://demo/camelQuartz"
                + "?cron=0/50 * * * * ?"
                + "&stateful=true")
                .routeId("demoCamelQuartzRoute")
                .autoStartup("{{demo.camelQuartzRoute.autoStartup}}")
                .process(exchange ->
                        log.info("In camel quartz processor ...")
                );
    }
}
