package com.savan.scheduling.controller;

import com.savan.scheduling.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")
public class CronTaskController {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("/start")
    public String startTask(@RequestParam String taskName, @RequestParam String cronExp){
        String resp = scheduledTaskService.startTask(taskName, cronExp);
        // #Set task running status to active(I)
        System.out.println("Task status set to 'A'");
        return resp;
    }

    @PostMapping("/stop")
    public String stopTask(@RequestParam String taskName) {
        return scheduledTaskService.stopTask(taskName);
    }

}
