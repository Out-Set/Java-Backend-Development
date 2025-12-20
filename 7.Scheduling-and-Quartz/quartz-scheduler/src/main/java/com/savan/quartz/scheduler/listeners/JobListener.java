package com.savan.quartz.scheduler.listeners;

import com.savan.quartz.scheduler.service.IJobListener;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobListener implements IJobListener {

	@Override
	public String getName() {
		return "APIFlowJobListner";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.info("JobToBeExecuted : {}", context.getJobDetail().getKey());
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.info("JobExecutionVetoed : {}", context.getJobDetail().getKey());
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.info("JobWasExecuted : {} and JobException is: {}", context.getJobDetail().getKey(), jobException);
		
	}

}
