package com.spring.scheduler.DynamicCron.controller;

import com.spring.scheduler.DynamicCron.entity.CommonLogs;
import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.service.CommonLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/logs")
public class CommonLogsController {

    @Autowired
    private CommonLogsService commonLogsService;

    @GetMapping("/getCommonLogs")
    public List<CommonLogs> getCommonLogs(){
        return commonLogsService.getAllCommonLogs();
    }
}
