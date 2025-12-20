package com.spring.scheduling.conditional;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// You first store it in app.prop then from there put it here.

@Component
public class ParameterizingTheSchedule {

    private final static Logger LOG = LoggerFactory.getLogger(ParameterizingTheSchedule.class);
//    @Scheduled(cron = "${cron.expression}")
    public void paramWithCron() {
        LOG.info("Param with Cron:");
        System.out.println("cron task - " + System.currentTimeMillis() / 1000);
    }

//    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void paramWithFixedRate() {
        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

//    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void paramWithFixedDelay() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

}
