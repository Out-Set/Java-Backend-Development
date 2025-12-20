package com.spring.scheduling.conditional;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

// Whether the task is completed or not task will be rescheduled each time, after the given time interval.

@Component
public class FixedRate {
    int i = 0;
    Logger log = LoggerFactory.getLogger(FixedRate.class);
//    @Scheduled(fixedRate = 2000)
    public void scheduleTask() {

        i++;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println("Fixed rate Scheduler: Task running at - " + strDate);
        log.info("Log info: " + i);
    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed rate task with one second initial delay - " + now);
    }

//    @Scheduled(cron = )
    public void scheduleTaskWithCron() {
        i++;
        Logger log = LoggerFactory.getLogger(FixedRate.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println("Fixed rate Scheduler: Task running at - " + strDate);
        log.info("Log info: {0}" + i);
    }
}
