package com.savan.springboot_profiles.schedulers;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Profile({"dev", "master"})
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "spring.cron.enabled", havingValue = "true")
public class SpringScheduler {

    @Scheduled(cron = "*/10 * * * * *")
    public void runTask(){
        System.out.println("Spring Scheduler :: "+ LocalDateTime.now());
    }
}
