package com.pk.quartz.scheduler.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.service.QuartzJobSchedulerService;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/scheduler")
@Slf4j
public class SchedulerController {

    @Inject
    private QuartzJobSchedulerService quartzJobSchedulerService;

    @PostMapping("/createQuartzTrigger")
    public ResponseEntity<String> createQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) {
    	try {  
    		quartzJobSchedulerService.createQuartzTrigger(jobSchedulerDetail);
    		} catch (Exception var5) {
    		log.error(var5.getMessage(), var5);
    		}
        return ResponseEntity.ok("Job Scheduled: " + jobSchedulerDetail.getJobName());
    }
    
    @PostMapping("/updateQuartzTrigger")
    public ResponseEntity<String> updateQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) {
    	try {  
    		quartzJobSchedulerService.updateQuartzTrigger(jobSchedulerDetail);
    		} catch (Exception var5) {
    		log.error(var5.getMessage(), var5);
    		}
        return ResponseEntity.ok("Job Updated: " + jobSchedulerDetail.getJobName());
    }
    
    @PostMapping("/deleteQuartzTrigger")
    public ResponseEntity<String> deleteQuartzTrigger(@RequestBody JOBSchedulerDetail jobSchedulerDetail) {
    	try {  
    		quartzJobSchedulerService.deleteQuartzTrigger(jobSchedulerDetail);
    		} catch (Exception var5) {
    		log.error(var5.getMessage(), var5);
    		}
        return ResponseEntity.ok("Job Deleted: " + jobSchedulerDetail.getJobName());
    }
    
    @PostMapping("/pause")
    public ResponseEntity<String> pauseJob(@RequestParam String triggerName, @RequestParam String triggerGroupName) {
        try {
        	quartzJobSchedulerService.pauseJob(triggerName, triggerGroupName);
            return ResponseEntity.ok("Job paused: " + triggerName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to pause job: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteJob(@RequestParam String triggerName, @RequestParam String triggerGroupName) {
        try {
        	quartzJobSchedulerService.deleteJob(triggerName, triggerGroupName);
            return ResponseEntity.ok("Job deleted: " + triggerName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete job: " + e.getMessage());
        }
    }
    
    @PostMapping("/resume")
    public ResponseEntity<String> resumeJob(@RequestParam String triggerName, @RequestParam String triggerGroupName) {
        try {
        	quartzJobSchedulerService.resumeJob(triggerName, triggerGroupName);
            return ResponseEntity.ok("Job resumed: " + triggerName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to resume job: " + e.getMessage());
        }
    }
    
    @PostMapping("/getAllProcessData")
    public Map<String, SchedulerJobExecutionMonitorVO> getAllProcessData() {
        try {            
            Map<String, SchedulerJobExecutionMonitorVO> jobsData =  quartzJobSchedulerService.getAllProcessData();
            log.info("Jobs data:{}", jobsData);
            return jobsData;
        } catch (Exception e) {
            log.error("Exception occured: " + e);
        }
        return null;
    }
}
