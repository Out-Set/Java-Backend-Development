package com.spring.scheduler.DynamicCron.controller;

import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.service.ScheduledTaskService;
import com.spring.scheduler.DynamicCron.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("/resetCronExpression")
    public String resetCron(@RequestParam String taskName, @RequestParam String cronExpression) {

//        ScheduledTask scheduledTask = scheduledTaskService.findByTaskName(taskName);
//        String taskType = scheduledTask.getTaskType();
//        String resp = taskService.startTask(taskName, cronExpression, taskType, scheduledTask);
//        scheduledTaskService.updateStatusOfProgActive(scheduledTask.getTaskName());
        return scheduledTaskService.updateCronExpression(cronExpression, taskName);
    }
    // http://localhost:8082/tasks/start?taskId=cron-2&cronExpression=0/10 * * * * *

    @PostMapping("/stop")
    public String stopTask(@RequestParam String taskName) {
        String resp = taskService.stopTask(taskName);
        scheduledTaskService.updateStatusOfProgNotActive(taskName);
        return resp;
    }
    // http://localhost:8082/tasks/stop?taskName=cron-1

    @PostMapping("/start")
    public String startTask(@RequestParam String taskName){

        ScheduledTask scheduledTask = scheduledTaskService.findByTaskName(taskName);

        String resp = taskService.startTask(taskName, scheduledTask.getCronExpression(), scheduledTask.getTaskType(), scheduledTask);
        scheduledTaskService.updateStatusOfProgActive(scheduledTask.getTaskName());

        return resp;
    }
}
