package com.spring.scheduling.conditional;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class BreakTheSchedule {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

//    @Scheduled(fixedRate = 2000)
    public void scheduleByFixedRate() throws Exception {
        System.out.println("Scheduler is executing "+ format.format(Calendar.getInstance().getTime())+"\n");
    }
}


