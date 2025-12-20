package com.savan.quartz.scheduler.jobs;

import com.savan.quartz.scheduler.service.IGenericJOBExecutorAuxiliaryService;
import com.savan.quartz.scheduler.utils.JOBSchedulerHelper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class GenericJOBExecutor extends QuartzJobBean implements InterruptableJob {

    @Autowired
    private JOBSchedulerHelper jobSchedulerHelper;

	@Autowired
	private IGenericJOBExecutorAuxiliaryService genericJOBExecutorAuxiliaryService;

    private Thread currentThread;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	currentThread = Thread.currentThread();
    	String compositeJobAndTriggerKey = prepareCompositeJobAndTriggerKey(context);
    	genericJOBExecutorAuxiliaryService.executeInternal(compositeJobAndTriggerKey, context);
    }
    
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        if (currentThread != null) {
            log.error("Interrupting thread: {}", currentThread.getName());
            currentThread.interrupt();
        } else {
            log.warn("No thread to interrupt.");
        }
    }
    
	private String prepareCompositeJobAndTriggerKey(JobExecutionContext context) {
		return jobSchedulerHelper.prepareKeyForJobExecution(context.getTrigger().getJobKey().getGroup(),
				context.getTrigger().getJobKey().getName(), context.getTrigger().getKey().getGroup(),
				context.getTrigger().getKey().getName());
	}

	private JobKey getJobKey(JobExecutionContext context) {
		return context.getTrigger().getJobKey();
	}

	private TriggerKey getTriggerKey(JobExecutionContext context) {
		return context.getTrigger().getKey();
	}
}
