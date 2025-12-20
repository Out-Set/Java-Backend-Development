package com.tcl.messageService.controller;

import com.tcl.messageService.entity.ScheduledTask;
import com.tcl.messageService.service.CronTaskService;
import com.tcl.messageService.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")
public class CronTaskController {

    @Autowired
    private CronTaskService cronTaskService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("/start")
    public String startTask(@RequestParam String taskName){

        ScheduledTask scheduledTask = scheduledTaskService.findByTaskName(taskName);

        String resp = cronTaskService.startTask(scheduledTask.getTaskType(), taskName, scheduledTask.getCronExpression(), scheduledTask);
        scheduledTaskService.updateStatusOfProgActive(taskName);

        return resp;
    }

    @PostMapping("/stop")
    public String stopTask(@RequestParam String taskName) {
        return cronTaskService.stopTask(taskName);
    }

    @PostMapping("/resetCronExpression")
    public String resetCron(@RequestParam String cronExpression, @RequestParam String taskName) {
        return scheduledTaskService.updateCronExpression(cronExpression, taskName);
    }
    //http://192.168.1.19:8080/tasks/resetCronExpression?taskName=undefined&cronExpression=0/15%20*%20*%20*%20*%20*
}
