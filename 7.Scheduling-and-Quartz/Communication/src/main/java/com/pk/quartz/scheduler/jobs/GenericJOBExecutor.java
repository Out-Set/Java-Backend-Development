package com.pk.quartz.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pk.quartz.scheduler.serviceinterface.IGenericJOBExecutorAuxiliaryService;
import com.pk.quartz.scheduler.util.JOBSchedulerHelper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@PersistJobDataAfterExecution
@DisallowConcurrentExecution
//@Named("genericJOBExecutor")
public class GenericJOBExecutor extends QuartzJobBean implements InterruptableJob {
	@Inject
	private IGenericJOBExecutorAuxiliaryService genericJOBExecutorAuxiliaryService;
	@Inject
	private JOBSchedulerHelper jobSchedulerHelper;
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
