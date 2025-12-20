package com.savan.springboot_profiles.schedulers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestCamelSchedulers extends RouteBuilder {

    // Since @Profile() works only at class level, so we can not use it at method level
    // This is how we can profile at method level with the help of 'Environment'
    // Either use @Profile() at class level or use as shown below

    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {
        configureCamelSchedulerA();
        configureCamelSchedulerB();
    }

    @ConditionalOnProperty(name = "camel.master.cron.enabled", havingValue = "true")
    public void configureCamelSchedulerA() throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("master")) {
            from("quartz://myGroup/myCamelCronJobA?cron=*/10 * * * * ?")
                    .log("Camel Master Scheduler - configureCamelSchedulerA() :: ${date:now:yyyy-MM-dd HH:mm:ss}");
        }
    }

    @ConditionalOnProperty(name = "camel.slave.cron.enabled", havingValue = "true")
    public void configureCamelSchedulerB() throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("slave")) {
            from("quartz://myGroup/myCamelCronJobB?cron=*/10 * * * * ?")
                    .log("Camel Slave Scheduler - configureCamelSchedulerB() :: ${date:now:yyyy-MM-dd HH:mm:ss}");
        }
    }
}


