package com.pk.quartz.scheduler.serviceinterface;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;

public interface IGenericJOBExecutorAuxiliaryService {
	void executeInternal(String var1, String var2, List<JOBSchedulerDetail> var3, JobKey var4, TriggerKey var5);

	void executeInternal(String compositeJobAndTriggerKey, JobExecutionContext context);

}