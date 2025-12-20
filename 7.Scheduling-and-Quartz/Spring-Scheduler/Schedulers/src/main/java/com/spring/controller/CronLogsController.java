package com.spring.controller;

import com.spring.entity.Cron1Logs;
import com.spring.entity.Cron2Logs;
import com.spring.service.CronLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CronLogsController {

    @Autowired
    private CronLogsService cronLogsService;

    @GetMapping("/getCron1Logs")
    public List<Cron1Logs> getCron1Log(){
        return cronLogsService.getCron1Logs();
    }

    @GetMapping("/getCron2Logs")
    public List<Cron2Logs> getCron2Log(){
        return cronLogsService.getCron2Logs();
    }
}
