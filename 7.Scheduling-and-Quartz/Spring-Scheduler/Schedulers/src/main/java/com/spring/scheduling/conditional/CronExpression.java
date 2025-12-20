package com.spring.scheduling.conditional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CronExpression {

    final String val = "*/5 * * * * *";

//    @Scheduled(cron = val)
    public void run(){
        System.out.println("Through Cron Expression ... ");
    }

//    @Scheduled(cron = "${dynamicCronExpression}")
    public void executeTask() {
        System.out.println("Running in Dynamic Cron!, Having Cron Expression: ${dynamicCronExpression}");
    }
}
