package com.spring.scheduling.conditional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

// Preferred where we have synchronous tasks(Dependent on each other).
// Only after completion of the current task there will be a fixed-delay of 2 sec.
@Component
public class FixedDelay {

    int i = 0;
    Logger log = LoggerFactory.getLogger(FixedRate.class);

//    @Scheduled(fixedDelay = 2000)
    public void scheduleTask() throws InterruptedException {

        i++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println("Fixed rate Scheduler: Task running at - " + strDate);
        log.info("Log info: " + i);

        Thread.sleep(2000);
    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Fixed rate task with one second initial delay - " + now);
    }
}