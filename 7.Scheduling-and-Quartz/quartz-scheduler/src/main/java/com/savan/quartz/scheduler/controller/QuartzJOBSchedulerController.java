package com.savan.quartz.scheduler.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.service.IQuartzJOBSchedulerService;
import com.savan.quartz.scheduler.vo.JOBSchedulerDetailVO;
import com.savan.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/quartz/scheduler")
public class QuartzJOBSchedulerController {

    @Autowired
    private IQuartzJOBSchedulerService quartzJOBSchedulerService;

    @GetMapping("/read/{id}")
    public ResponseEntity<JOBSchedulerDetailVO> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<JOBSchedulerDetailVO>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.readAll());
    }

    @PostMapping("/createQuartzTrigger")
    public ResponseEntity<ResponseDto> createQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.createQuartzTrigger(jobSchedulerDetail),
                HttpStatus.CREATED.value()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/updateQuartzTrigger")
    public ResponseEntity<ResponseDto> updateQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.updateQuartzTrigger(jobSchedulerDetail),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/deleteQuartzTrigger")
    public ResponseEntity<ResponseDto> deleteQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.deleteQuartzTrigger(jobSchedulerDetail),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/pause")
    public ResponseEntity<ResponseDto> pauseJob(@RequestParam String jobName, @RequestParam Long processGroupHeaderId) throws SchedulerException {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.pauseJob(jobName, processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/resume")
    public ResponseEntity<ResponseDto> resumeJob(@RequestParam String jobName, @RequestParam Long processGroupHeaderId) throws SchedulerException {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.resumeJob(jobName, processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteJob(@RequestParam String jobName, @RequestParam Long processGroupHeaderId) throws SchedulerException {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.deleteJob(jobName, processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/getAllProcessData")
    public Map<String, SchedulerJobExecutionMonitorVO> getAllProcessData() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.getAllProcessData()).getBody();
    }

    @PostMapping("/previousAndNextFireTime")
    public ResponseEntity<Pair<Date, Date>> getQuartzTriggerPreviousAndNextFireTime(@RequestBody JOBSchedulerDetail jobSchedulerDetail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.getQuartzTriggerPreviousAndNextFireTime(jobSchedulerDetail));
    }

    @GetMapping("/previousAndNextFireTime")
    public ResponseEntity<Pair<Date, Date>> getQuartzTriggerPreviousAndNextFireTime(@RequestParam String triggerName, @RequestParam String triggerGroupName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.getQuartzTriggerPreviousAndNextFireTime(triggerName, triggerGroupName));
    }

    @GetMapping("/pauseJOBGroup")
    public ResponseEntity<ResponseDto> pauseJOBGroup(@RequestParam Long processGroupHeaderId){
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.pauseJOBGroup(processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/resumeJOBGroup")
    public ResponseEntity<ResponseDto> resumeJOBGroup(@RequestParam Long processGroupHeaderId) {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.resumeJOBGroup(processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/killJOBGroup")
    public ResponseEntity<ResponseDto> killJOBGroup(@RequestParam Long processGroupHeaderId) {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.killJOBGroup(processGroupHeaderId),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/pauseAll")
    public ResponseEntity<ResponseDto> pauseAll() {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.pauseAll(),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/resumeAll")
    public ResponseEntity<ResponseDto> resumeAll() {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.resumeAll(),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/killAll")
    public ResponseEntity<ResponseDto> killAll() {
        ResponseDto response = new ResponseDto(
                null,
                quartzJOBSchedulerService.killAll(),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/getJobGroupsWithRunningStatus")
    public ResponseEntity<Map<String, Map<String, String>>> getJobGroupsWithRunningStatus() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quartzJOBSchedulerService.getJobGroupsWithRunningStatus());
    }

}