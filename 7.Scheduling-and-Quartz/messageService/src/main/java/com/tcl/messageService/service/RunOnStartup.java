package com.tcl.messageService.service;

import com.tcl.messageService.entity.ScheduledTask;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class RunOnStartup {
    @Autowired
    private CronTaskService cronTaskService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    // Start all the previously running tasks, on application startUp
    @PostConstruct
    public void startTasksOnStartUp() {
        List<ScheduledTask> tasks = scheduledTaskService.readAll();
        System.out.println(tasks);

        for (ScheduledTask scheduledTask : tasks) {
            if(scheduledTask.getStatus().equals("TRUE")){
                String cronExpression = scheduledTask.getCronExpression();
                String taskType = scheduledTask.getTaskType();
                String taskName = scheduledTask.getTaskName();
                cronTaskService.startTask(taskType, taskName, cronExpression, scheduledTask);
            }
        }
    }
}





