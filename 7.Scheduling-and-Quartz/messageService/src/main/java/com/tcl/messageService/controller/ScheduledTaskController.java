package com.tcl.messageService.controller;

import com.tcl.messageService.entity.ScheduledTask;
import com.tcl.messageService.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/scheduledTask")
public class ScheduledTaskController {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("/create")
    public String create(@RequestBody ScheduledTask scheduledTask){
        return scheduledTaskService.create(scheduledTask);
    }

    @GetMapping("/read/{id}")
    public ScheduledTask readById(@PathVariable int id){
        return scheduledTaskService.readById(id);
    }

    @PostMapping("/update")
    public String update(@RequestParam int id, @RequestBody ScheduledTask scheduledTask){
        return scheduledTaskService.update(id, scheduledTask);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id){
        return scheduledTaskService.delete(id);
    }

    @GetMapping("/tasksWithStatus")
    public List<ScheduledTask> tasksWithStatus(){
        return scheduledTaskService.readAll();
    }
}
