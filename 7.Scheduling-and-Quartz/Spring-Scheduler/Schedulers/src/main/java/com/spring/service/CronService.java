package com.spring.service;

import com.spring.entity.Cron1Logs;
import com.spring.repository.Cron1LogsRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class CronService {

    private String cronExpression1 = "0/2 * * * * *";
    @Bean
    public void cronJob1() {
        final long now = System.currentTimeMillis() / 1000;
        System.out.println("Dynamic Scheduling, With cron expression: "+ cronExpression1+" : "+now);
    }


    private String cronExpression2 = "0/5 * * * * *";
    public void cronJob2() {
        final long now = System.currentTimeMillis() / 1000;
        System.out.println("Dynamic Scheduling, With cron expression: "+ cronExpression2+" : "+now);
    }
}
