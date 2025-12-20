package com.savan.scheduling.service;

import com.savan.scheduling.configs.ScheduledTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
public class ScheduledTaskService {

    @Autowired
    private CreatedTasks createdTasks;

    private final ScheduledTaskRegistrar taskRegistrar;
    private final Map<String, ScheduledTaskInfo> scheduledTasks;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    public ScheduledTaskService(ScheduledTaskRegistrar taskRegistrar, Map<String, ScheduledTaskInfo> scheduledTasks) {
        this.taskRegistrar = taskRegistrar;
        this.scheduledTasks = scheduledTasks;
    }

    // Task-Name will be registered as taskId in scheduledTasks map of spring
    public String startTask(String taskName, String cronExpression) {

        if (!scheduledTasks.containsKey(taskName)) {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);

            ScheduledFuture<?> future = Objects.requireNonNull(taskRegistrar.getScheduler()).schedule(
                    () -> {
                        System.out.println("Executing dynamic task for task ID: " + taskName);

                        executorService.submit(() -> createdTasks.executeTask1(taskName));
                        executorService.submit(() -> createdTasks.executeTask2(taskName));

                        // Start and NextStart Time for each execution
                        Date startTime = new Date();
                        System.out.println("Start Time: "+startTime);
                        Instant nextExecution = cronTrigger.nextExecution(new SimpleTriggerContext());
                        Date nextExecutionTime = (nextExecution != null) ? Date.from(nextExecution) : null;
                        System.out.println("Next Execution: "+nextExecutionTime);
                        System.out.println();
                    },
                    new CronTrigger(cronExpression)
            );

            // Start and NextStart Time for the very first time
            Date startTime = new Date();
            System.out.println("Start Time: "+startTime);
            Instant nextExecution = cronTrigger.nextExecution(new SimpleTriggerContext());
            Date nextExecutionTime = (nextExecution != null) ? Date.from(nextExecution) : null;
            System.out.println("Next Execution: "+nextExecutionTime);
            System.out.println();

            ScheduledTaskInfo taskInfo = new ScheduledTaskInfo(taskName, future, cronExpression);
            scheduledTasks.put(taskName, taskInfo);

            return "Task started successfully for task ID: " + taskName + ", Cron-Expression: " + taskInfo.getCronExpression();
        } else {
            return "Task with ID " + taskName + " is already running. To reschedule first stop it.";
        }
    }

    // Stop Task, Here taskName is as target
    public String stopTask(String taskName) {
        if (scheduledTasks.containsKey(taskName)) {
            ScheduledTaskInfo taskInfo = scheduledTasks.get(taskName);

            taskInfo.getScheduledFuture().cancel(true);
            scheduledTasks.remove(taskName);

            // #Set task running status to inactive(I)
            System.out.println("Task status set to 'I'");

            return "Task stopped successfully for task ID: " + taskName;
        } else {
            return "Task with ID " + taskName + " is not running.";
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}

