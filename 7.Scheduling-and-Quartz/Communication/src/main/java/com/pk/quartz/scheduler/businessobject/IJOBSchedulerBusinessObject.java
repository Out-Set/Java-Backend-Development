package com.pk.quartz.scheduler.businessobject;


import java.util.List;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;

public interface IJOBSchedulerBusinessObject {
	List<JOBSchedulerDetail> getNeoJobsByTriggerName(String var1);

	JOBSchedulerExecutionLog createJobExectutionLog(JOBSchedulerExecutionLog var1);

	List<JOBSchedulerDetail> getRegisteredJobs(Long var1);

	//List<JOBSchedulerDetailVO> getJOBSchedulerDetailByTriggerGroupAndName(String var1, String var2);

	//List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByJOBGroupAndName(String var1, String var2);

	//List<JOBSchedulerDetailVO> getJOBSchedulerDetailByTriggerGroupAndName(String var1, String var2, List<Integer> var3);

	//List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByJOBGroupAndName(String var1, String var2, List<Integer> var3);

	//List<JOBSchedulerDetail> getJobSchedulerProcessByProcessGroup(Long var1, List<Integer> var2);

	JOBSchedulerDetail getJOBSchedulerDetailByExecutionSequence(Long var1);

	JOBSchedulerDetail getActiveScheduledJobByJobName(String var1, List<Integer> var2);

	List<Long> getAllMappedProcessIdAccrossAllProcessGroups();

	List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2);

	List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2, List<Integer> var3);

	JOBSchedulerExecutionLog findById(Long jobLogId, Class<JOBSchedulerExecutionLog> class1);
}
