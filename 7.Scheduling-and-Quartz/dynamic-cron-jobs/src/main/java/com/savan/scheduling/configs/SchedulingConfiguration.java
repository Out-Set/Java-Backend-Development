package com.savan.scheduling.configs;

import com.savan.scheduling.service.ScheduledTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {

    @Bean
    public ScheduledTaskRegistrar taskRegistrar() {
        return new ScheduledTaskRegistrar();
    }

    @Bean
    public Map<String, ScheduledTaskInfo> scheduledTasks() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ScheduledTaskService taskService(ScheduledTaskRegistrar taskRegistrar, Map<String, ScheduledTaskInfo> scheduledTasks) {
        return new ScheduledTaskService(taskRegistrar, scheduledTasks);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
        // Configure tasks, if needed
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
