package com.savan.quartz.scheduler.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.savan.quartz.scheduler.vo.JOBSchedulerDetailVO;
import com.savan.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.transaction.annotation.Transactional;

public interface IQuartzJOBSchedulerService {

    JOBSchedulerDetailVO readById(Long id);
    List<JOBSchedulerDetailVO> readAll();

    @Transactional
    String createQuartzTrigger(JOBSchedulerDetail var1) throws Exception;
    @Transactional
    String updateQuartzTrigger(JOBSchedulerDetail var1) throws Exception;
    @Transactional
    String deleteQuartzTrigger(JOBSchedulerDetail var1) throws Exception;

    @Transactional
    String pauseJob(String jobName, Long processGroupId) throws SchedulerException;
    @Transactional
    String resumeJob(String jobName, Long processGroupId) throws SchedulerException;
    @Transactional
    String deleteJob(String jobName, Long processGroupId) throws SchedulerException;

    @Transactional
	String pauseJOBGroup(Long jobGroup);
    @Transactional
    String resumeJOBGroup(Long jobGroup);
    @Transactional
    String killJOBGroup(Long jobGroup);

    @Transactional
    String pauseAll();
    @Transactional
    String resumeAll();
    @Transactional
    String killAll();

    List<JobKey> getJobKeysList(List<JOBSchedulerDetail> jobSchedulerDetails);
    Map<String, Map<String, String>> getJobGroupsWithRunningStatus();

    Boolean isJobSchedulerThreadAlive(String var1);

    Map<String, SchedulerJobExecutionMonitorVO> getAllProcessData();
    Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(JOBSchedulerDetail var1);
    Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(String triggerName, String triggerGroupName);

	Integer doStart();
	Integer doStandBy();

	void fireStopCompletionEvent(Boolean var1, String var2);

}