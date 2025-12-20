package com.pk.quartz.scheduler.serviceinterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;

public interface IQuartzJOBSchedulerService {
	List<JOBSchedulerDetail> getNeoJobsByTriggerName(String var1);

	JOBSchedulerExecutionLog createJobExectutionLog(JOBSchedulerExecutionLog var1);

	Integer createQuartzTrigger(JOBSchedulerDetail var1);

	Integer updateQuartzTrigger(JOBSchedulerDetail var1);

	Integer deleteQuartzTrigger(JOBSchedulerDetail var1);

	List<JOBSchedulerDetail> getRegisteredJobs(Long var1);

	JOBSchedulerExecutionLog updateJobExectutionLog(Long var1);

	Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(JOBSchedulerDetail var1);

	

	List<JOBSchedulerDetail> getJobSchedulerProcessByProcessGroup(Long var1, List<Integer> var2);

	JOBSchedulerDetail getJOBSchedulerDetailByExecutionSequence(Long var1);

	List<Long> getAllMappedProcessIdAccrossAllProcessGroups();

	JOBSchedulerDetail getActiveScheduledJobByJobName(String var1, List<Integer> var2);

	Integer pauseJOBGroup(String var1);

	Integer resumeJOBGroup(String var1);

	Integer killJOBGroup(String var1);

	Map<String, List<JOBSchedulerDetail>> pauseAll();

	Map<String, List<JOBSchedulerDetail>> resumeAll();

	Map<String, List<JOBSchedulerDetail>> killAll();

	List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2);

	List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2, List<Integer> var3);

	Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(String var1, String var2);

	Integer doStart();

	Integer doStandBy();

	Boolean isJobSchedulerThreadAlive(String var1);

	void fireStopCompletionEvent(Boolean var1, String var2);

}