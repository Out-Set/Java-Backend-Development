package com.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.scheduling.dynamic.StartAndStopScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class StartAndStopScheduleController {

    // Stop and Start scheduler
    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    private StartAndStopScheduler startAndStopScheduler;

    private static final String SCHEDULED_TASKS = "scheduledTasks";

    @GetMapping(value = "/stop")
    public String stopSchedule() {
        postProcessor.postProcessBeforeDestruction(startAndStopScheduler, SCHEDULED_TASKS);
        return "Stopped";
    }

    @GetMapping(value = "/start")
    public String startSchedule() {
        postProcessor.postProcessAfterInitialization(startAndStopScheduler, SCHEDULED_TASKS);
        return "Started";
    }

    @GetMapping(value = "/list")
    public String listSchedules() throws JsonProcessingException {
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
        if (!setTasks.isEmpty()) {
            return setTasks.toString();
        } else {
            return "Currently no scheduler tasks are running";
        }
    }

}
