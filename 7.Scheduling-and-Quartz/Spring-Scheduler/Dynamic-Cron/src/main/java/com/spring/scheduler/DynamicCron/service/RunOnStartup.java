package com.spring.scheduler.DynamicCron.service;

import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class RunOnStartup {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    // Start all the tasks, on application startUp
    @PostConstruct
    public void startTasksOnStartUp() {
        List<ScheduledTask> tasks = scheduledTaskService.getAllTasks();
        System.out.println(tasks);

        for (ScheduledTask scheduledTask : tasks) {
            String taskName = scheduledTask.getTaskName();
            String cronExpression = scheduledTask.getCronExpression();
            String taskType = scheduledTask.getTaskType();
            taskService.startTask(taskName, cronExpression, taskType, scheduledTask);
            scheduledTaskService.updateStatusOfProgActive(scheduledTask.getTaskName());
        }

        System.out.println();
        System.out.println();
        List<String> target = scheduledTaskService.findTaskNameByTaskType("proc");
        System.out.println("findTaskNameByTaskType: "+target);

        ScheduledTask task = scheduledTaskService.findByTaskName("proc-1");
        System.out.println("findByTaskName: "+task);

        System.out.println();
        System.out.println();
    }
}
