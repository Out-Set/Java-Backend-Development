package com.spring.controller;

import com.spring.entity.Cron1Logs;
import com.spring.service.CronService;
import com.spring.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/dynamicSchedule")
public class DynamicScheduleController {

    @Autowired
    private CronService cronService;
    @PostMapping("/cron1Val")
    public String getCron1Value(@RequestBody String expression) {
        String decodedInput = URLDecoder.decode(expression, StandardCharsets.UTF_8);
        String requiredSubString = decodedInput.substring(0, decodedInput.length() - 1);
        cronService.setCronExpression1(requiredSubString);
        return requiredSubString;
    }

    @PostMapping("/cron2Val")
    public String getCron2Value(@RequestBody String expression) {
        String decodedInput = URLDecoder.decode(expression, StandardCharsets.UTF_8);
        String requiredSubString = decodedInput.substring(0, decodedInput.length() - 1);
        cronService.setCronExpression2(requiredSubString);
        return requiredSubString;
    }

    @Autowired
    private DelayService delayService;
    @GetMapping("/delayVal")
    public long setDelayValue(@RequestParam long delay) {
        System.out.println(delay);
        delayService.setDelay(delay);
        return delay;
    }
}