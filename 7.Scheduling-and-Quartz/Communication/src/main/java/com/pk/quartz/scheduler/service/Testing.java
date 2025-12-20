package com.pk.quartz.scheduler.service;

import org.quartz.JobExecutionContext;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;

import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Named
@Slf4j
public class Testing {
	
	public void method1(JobExecutionContext context) throws InterruptedException {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) context.getMergedJobDataMap().get("jobSchedulerDetail");

		log.info("Environment: {}", jobSchedulerDetail.getEnvironment());
		log.info("Method one 1 executed for trigger: {}",context.getTrigger().getKey());
		Thread.sleep(15000);
	}
	
	public void method2() {
		log.info("Method one 2 executed");
	}
	
	public void method3() {
		log.info("Method one 3 executed");
	}

}
