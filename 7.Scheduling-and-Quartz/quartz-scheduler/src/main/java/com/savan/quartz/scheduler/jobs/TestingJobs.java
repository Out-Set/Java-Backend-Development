package com.savan.quartz.scheduler.jobs;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import org.quartz.JobExecutionContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestingJobs {
	
	public void method1(JobExecutionContext context) throws InterruptedException {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) context.getMergedJobDataMap().get("jobSchedulerDetail");
		log.info("Environment: {}", jobSchedulerDetail.getEnvironment());
		log.info("Method-1 executed for trigger: {}", context.getTrigger().getKey());
		Thread.sleep(15000);
	}
	
	public void method2(JobExecutionContext context) {
		log.info("Method-2 executed for trigger: {}", context.getTrigger().getKey());
	}
	
	public void method3(JobExecutionContext context) {
        log.info("Method-3 executed for trigger: {}", context.getTrigger().getKey());
	}

}
