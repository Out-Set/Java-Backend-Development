package com.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.scheduling.dynamic.DynamicSchedulingWithCron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StartAndStopCronController {
    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    private DynamicSchedulingWithCron dynamicSchedulingWithCron;

    private static final String SCHEDULED_TASKS = "scheduledTasks";

    @GetMapping(value = "/stopCron")
    public String stopSchedule() {
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
        postProcessor.postProcessBeforeDestruction(dynamicSchedulingWithCron, "cronJob1()");

//        postProcessor.destroy();

        return "Stopped";
    }

    @GetMapping(value = "/startCron")
    public String startSchedule() {
        postProcessor.postProcessAfterInitialization(dynamicSchedulingWithCron, SCHEDULED_TASKS);
        return "Started";
    }

    @GetMapping(value = "/listCron")
    public String listSchedules() throws JsonProcessingException {
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();

        System.out.println("Tasks---------------------: "+setTasks.stream().collect(Collectors.toList()).get(0));
        System.out.println("Tasks---------------------: "+setTasks.stream().collect(Collectors.toList()).get(1));

        // getClass().getName()
        System.out.println("Tasks---------------------: "+setTasks.stream().collect(Collectors.toList()).get(0).getClass().getName());
        System.out.println("Tasks---------------------: "+setTasks.stream().collect(Collectors.toList()).get(1).getClass().getName());

        if (!setTasks.isEmpty()) {
            return setTasks.toString();
        } else {
            return "Currently no scheduler tasks are running";
        }
    }
}
