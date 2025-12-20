package com.app1.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamelQuartz extends RouteBuilder {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${cron.expression}")
    private String cronExp;
    private Route testRoute;

    @Override
    public void configure() throws Exception {

        // Set up the Quartz component
        from("quartz://myGroup/myTimerName?cron=0/10+*+*+*+*+?")
                .process(exchange -> {
                    System.out.println("Started ...");
                    for (int i = 1; i <= 5; i++) {
                        System.out.println("i = " + i);
                    }
                    System.out.println("DB URL: " + url);
                    System.out.println("Cron Expression: " + cronExp);
                    System.out.println();
                });
//            .to("direct:start");


        String cron = "0/1 * * * * ?";
        CronScheduledRoutePolicy startPolicy = new CronScheduledRoutePolicy();
        startPolicy.setRouteStartTime(cron);

        from("direct:startQuartz")
                .routeId("testRoute")
                .routePolicy(startPolicy)
                .noAutoStartup()
                .log("Starting route at ${date:now:yyyy-MM-dd HH:mm:ss}")
                .process(exchange -> {
                    System.out.println("Started ...");
                    for (int j = 1; j <= 2; j++) {
                        System.out.println("j = " + j);
                    }
                    System.out.println();
                });

        // Start the route
        startPolicy.startRoute(testRoute);
    }
}


