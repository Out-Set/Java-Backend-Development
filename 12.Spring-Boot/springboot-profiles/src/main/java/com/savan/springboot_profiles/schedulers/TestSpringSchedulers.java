package com.savan.springboot_profiles.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class TestSpringSchedulers {

    // Since @Profile() works only at class level, so we can not use it at method level
    // This is how we can profile at method level with the help of 'Environment'
    // Either use @Profile() at class level or use as shown below

    @Autowired
    private Environment env;

    @ConditionalOnProperty(name = "spring.master.cron.enabled", havingValue = "true")
    @Scheduled(cron = "*/10 * * * * *")
    public void runTaskA() {
        if (Arrays.asList(env.getActiveProfiles()).contains("master")) {
            System.out.println("Spring Master Scheduler :: "+ LocalDateTime.now());
        }
    }

    @ConditionalOnProperty(name = "spring.slave.cron.enabled", havingValue = "true")
    @Scheduled(cron = "*/10 * * * * *")
    public void runTaskB(){
        if (Arrays.asList(env.getActiveProfiles()).contains("slave")) {
            System.out.println("Spring Slave Scheduler :: "+ LocalDateTime.now());
        }
    }

}
