package com.spring.scheduler.DynamicCron.controller;

import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/scheduledTask")
public class ScheduledTaskController {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("")
    public ScheduledTask saveTask(@RequestBody ScheduledTask scheduledTask){
        return (ScheduledTask) scheduledTaskService.saveTask(scheduledTask);
    }

    @GetMapping("")
    public List<ScheduledTask> findAllTasks(){
        return scheduledTaskService.getAllTasks();
    }

    @GetMapping("/findByTask/{taskName}")
    public ScheduledTask findByProgName(@PathVariable String taskName){
        return scheduledTaskService.findByTaskName(taskName);
    }

    @GetMapping("/findTaskName/{taskType}")
    public List<String> findTargetNameByTaskName(@PathVariable String taskType){
        return scheduledTaskService.findTaskNameByTaskType(taskType);
    }

    @GetMapping("/executeProcedure1")
    public String executeProcedure(){
        scheduledTaskService.executeProcedure1();
        return "Executed successfully!";
    }

    @GetMapping("/executeProcedure2")
    public String executeProcedure2(){
        int id = 12;
        String name = "Sawan";
        scheduledTaskService.executeProcedure2(id, name);
        return "Executed successfully!";
    }

    @GetMapping("/tasksWithStatus")
    public List<ScheduledTask> tasksWithStatus(){
        return scheduledTaskService.tasksWithStatus();
    }
}
