package com.spring.scheduling.dynamic;

import com.spring.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// triggerTask()
// This method schedules a task to be run once, at a specific time.
// This is useful for tasks that need to be run at a specific time,
// such as sending a notification at a certain hour or starting a backup at a certain time of day.

//@EnableScheduling
//@Configuration
public class DynamicSchedulingWithDelay implements SchedulingConfigurer {

    @Autowired
    private DelayService delayService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                () -> delayService.tickWIthDelay(), context -> {
                    Optional<Date> lastCompletionTime = Optional.ofNullable(context.lastCompletionTime());
                    Instant nextExecutionTime = lastCompletionTime.orElseGet(Date::new).toInstant().plusMillis(delayService.getDelay());
                    return Date.from(nextExecutionTime).toInstant();
                }
        );
    }
}
