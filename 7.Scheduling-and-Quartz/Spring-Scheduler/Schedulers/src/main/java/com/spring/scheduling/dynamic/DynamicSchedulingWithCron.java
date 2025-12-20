package com.spring.scheduling.dynamic;

import com.spring.entity.Cron1Logs;
import com.spring.entity.Cron2Logs;
import com.spring.repository.Cron1LogsRepo;
import com.spring.repository.Cron2LogsRepo;
import com.spring.service.CronLogsService;
import com.spring.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
public class DynamicSchedulingWithCron implements SchedulingConfigurer {

    @Autowired
    private CronService cronService;
    @Autowired
    private CronLogsService cronLogsService;
    @Autowired
    private Cron1LogsRepo cron1LogsRepo;
    @Autowired
    private Cron2LogsRepo cron2LogsRepo;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());

        // Cron-1
        taskRegistrar.addTriggerTask(
                () -> cronService.cronJob1(),
                context -> {
                    CronTrigger cronTrigger = new CronTrigger(cronService.getCronExpression1());

                    Date start = new Date();
                    Date nextStart = Date.from(Objects.requireNonNull(cronTrigger.nextExecution(context)));

                    long count = this.cron1LogsRepo.count();
                    Cron1Logs cron1Logs = new Cron1Logs();
                    cron1Logs.setLogID((int)++count);
                    cron1Logs.setStartTime(start);
                    cron1Logs.setNextStartTime(nextStart);

                    cronLogsService.saveCron1Logs(cron1Logs);

                    System.out.println("Start Time: "+start);
                    System.out.println("Next execution: "+nextStart);

                    return nextStart.toInstant();
//                    return cronTrigger.nextExecution(context);
                }
        );

        // Cron-2
        taskRegistrar.addTriggerTask(
                () -> cronService.cronJob2(),
                context -> {
                    CronTrigger cronTrigger = new CronTrigger(cronService.getCronExpression2());

                    Date start = new Date();
                    Date nextStart = Date.from(Objects.requireNonNull(cronTrigger.nextExecution(context)));

                    long count = this.cron2LogsRepo.count();
                    Cron2Logs cron2Logs = new Cron2Logs();
                    cron2Logs.setLogID((int)++count);
                    cron2Logs.setStartTime(start);
                    cron2Logs.setNextStartTime(nextStart);

                    cronLogsService.saveCron2Logs(cron2Logs);

                    System.out.println("Start Time: "+start);
                    System.out.println("Next execution: "+nextStart);

                    return nextStart.toInstant();
//                    return cronTrigger.nextExecution(context);
                }
        );
    }

}
