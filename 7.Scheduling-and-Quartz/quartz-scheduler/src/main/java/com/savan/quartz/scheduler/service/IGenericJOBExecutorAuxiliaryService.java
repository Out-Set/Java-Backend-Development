package com.savan.quartz.scheduler.service;

import java.util.List;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

public interface IGenericJOBExecutorAuxiliaryService {

    void executeInternal(String var1, String var2, List<JOBSchedulerDetail> var3, JobKey var4, TriggerKey var5);
	void executeInternal(String compositeJobAndTriggerKey, JobExecutionContext context);

}