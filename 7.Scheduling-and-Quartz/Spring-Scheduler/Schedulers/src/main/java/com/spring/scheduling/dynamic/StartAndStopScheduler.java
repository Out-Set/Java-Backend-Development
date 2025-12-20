package com.spring.scheduling.dynamic;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Configuration
@EnableScheduling
public class StartAndStopScheduler {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

//    @Scheduled(fixedRate = 2000)
    public void scheduleByFixedRate() throws Exception {
        System.out.println("Scheduler is executing "+ format.format(Calendar.getInstance().getTime()));
        System.out.println("Start and Stop Schedule ... ");
    }
}
