package com.savan.springboot_profiles.schedulers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "master"})
@Component
@ConditionalOnProperty(name = "camel.cron.enabled", havingValue = "true")
public class CamelScheduler extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("quartz://myGroup/myCamelCronJob?cron=*/10 * * * * ?")
                .log("Camel Scheduler :: ${date:now:yyyy-MM-dd HH:mm:ss}");

    }
}
