package com.savan.quartz.scheduler.serviceimpl;

import static com.savan.quartz.utils.ValidatorUtils.isNull;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerKey.triggerKey;

import java.util.*;
import java.util.stream.Collectors;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.service.IJOBSchedulerDetailService;
import com.savan.quartz.scheduler.service.IQuartzJOBSchedulerService;
import com.savan.quartz.scheduler.utils.SchedulerUtils;
import com.savan.quartz.scheduler.vo.JOBSchedulerDetailVO;
import com.savan.quartz.utils.ValidatorUtils;
import com.savan.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class QuartzJOBSchedulerServiceImpl extends JOBSchedulerDetailServiceImpl implements IQuartzJOBSchedulerService {

    @Autowired
    private SchedulerUtils schedulerUtils;

    @Autowired
    private SchedulerFactoryBean jobScheduler;

    @Autowired
    private SchedulerExecutionStateData schedulerExecutionStateData;

    @Autowired
    private IJOBSchedulerDetailService jobSchedulerDetailService;

    // Creates the new Quartz trigger
    public String createQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        try {
            JobDetail jobDetail = schedulerUtils.buildJobDetail(jobSchedulerDetail);
            Trigger trigger = schedulerUtils.prepareCronTrigger(jobSchedulerDetail);

            jobScheduler.getScheduler().scheduleJob(jobDetail, trigger);
            JOBSchedulerDetailVO savedJobSchedulerVo = jobSchedulerDetailService.create(jobSchedulerDetail);

            if (!jobSchedulerDetail.isActiveFlag())
                jobScheduler.getScheduler().pauseJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString()));
            return "Job with name: " + jobSchedulerDetail.getJobName() + ", scheduled successfully with id - " + savedJobSchedulerVo.getId();
        } catch (Exception e) {
            log.info("An error occurred in creating quartz scheduler: {}", e.getMessage());
            throw new RuntimeException("An error occurred in creating quartz scheduler with message: "+ e.getMessage());
        }
    }

    // Updates the Quartz trigger if it exists; otherwise creates a new one.
    public String updateQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        try {
            // Check if the trigger already exists
            if (schedulerUtils.isTriggerAlreadyConfigured(jobSchedulerDetail)) {
                // Reschedule the trigger with the new cron expression
                jobScheduler.getScheduler().rescheduleJob(
                        triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName()),
                        schedulerUtils.prepareCronTrigger(jobSchedulerDetail)
                );
                // Update job data if needed
                jobScheduler.getScheduler().getJobDetail(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString()))
                        .getJobBuilder()
                        .usingJobData(schedulerUtils.prepareJobDataForChainedExecution(jobSchedulerDetail));
                jobSchedulerDetailService.update(jobSchedulerDetail);

                // Pause or resume job based on active flag
                if (!jobSchedulerDetail.isActiveFlag())
                    jobScheduler.getScheduler().pauseJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString()));
                else
                    jobScheduler.getScheduler().resumeJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString()));
            } else {
                // Trigger doesn't exist, create a new one
                createQuartzTrigger(jobSchedulerDetail);
            }
            // If next fire time is null, create the trigger again
            if (jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())) == null ||
                    jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())).getNextFireTime() == null) {
                createQuartzTrigger(jobSchedulerDetail);
            }
            return "Job with name: " + jobSchedulerDetail.getJobName() + ", updated successfully!";
        } catch (Exception e) {
            log.info("An error occurred in updating quartz scheduler: {}", e.getMessage());
            throw new RuntimeException("An error occurred in updating quartz scheduler with message: "+ e.getMessage());
        }
    }

    // Deletes Quartz trigger
    public String deleteQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) throws Exception {
        try {
            if (ValidatorUtils.notNull(jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())))) {
                jobScheduler.getScheduler().unscheduleJob(triggerKey(jobSchedulerDetail.getTriggerName()));
                jobScheduler.getScheduler().deleteJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString()));
                jobSchedulerDetailService.deleteById(jobSchedulerDetail.getId());
                return "Job with name: " + jobSchedulerDetail.getJobName() + ", deleted successfully";
            }
            return "Job with name: " + jobSchedulerDetail.getJobName() + ", does not exists";
        } catch (Exception e) {
            log.info("An error occurred in deleting quartz scheduler: {}", e.getMessage());
            throw new RuntimeException("An error occurred in deleting quartz scheduler with message: "+ e.getMessage());
        }
    }

    // Pauses the Job
    public String pauseJob(String jobName, Long processGroupHeaderId) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, processGroupHeaderId.toString());
        if (!jobScheduler.getScheduler().checkExists(jobKey)) {
            throw new SchedulerException("Job with name: " + jobName + ", does not exists in group: " + processGroupHeaderId);
        }
        jobScheduler.getScheduler().pauseJob(jobKey);
        List<JOBSchedulerDetail> jobSchedulerDetails = findByProcessGroupHeaderIdAndJobName(processGroupHeaderId, jobName);
        jobSchedulerDetails.forEach(job -> job.setActiveFlag(false));
        updateAll(jobSchedulerDetails);
        return "Job with name: " + jobName + ", paused in group: " + processGroupHeaderId;
    }

    // Resumes the Job
    public String resumeJob(String jobName, Long processGroupHeaderId) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, processGroupHeaderId.toString());
        if (!jobScheduler.getScheduler().checkExists(jobKey)) {
            throw new SchedulerException("Job with name: " + jobName + ", does not exists in group: " + processGroupHeaderId);
        }
        jobScheduler.getScheduler().resumeJob(jobKey);
        List<JOBSchedulerDetail> jobSchedulerDetails = findByProcessGroupHeaderIdAndJobName(processGroupHeaderId, jobName);
        jobSchedulerDetails.forEach(job -> job.setActiveFlag(true));
        updateAll(jobSchedulerDetails);
        return "Job with name: " + jobName + ", resumed in group: " + processGroupHeaderId;
    }

    // Deletes the Job
    public String deleteJob(String jobName, Long processGroupHeaderId) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, processGroupHeaderId.toString());
        if (!jobScheduler.getScheduler().checkExists(jobKey)) {
            throw new SchedulerException("Job with name: " + jobName + ", does not exists in group: " + processGroupHeaderId);
        }
        jobScheduler.getScheduler().deleteJob(jobKey);
        deleteAllById(findByProcessGroupHeaderIdAndJobName(processGroupHeaderId, jobName).stream()
                .map(JOBSchedulerDetail::getId)
                .collect(Collectors.toList()));
        return "Job with name: " + jobName + ", deleted in group: " + processGroupHeaderId;
    }

    @Override
    public String pauseJOBGroup(Long processGroupHeaderId) {
        List<JOBSchedulerDetail> jobSchedulerDetails = findByProcessGroupHeaderId(processGroupHeaderId);
        for (JOBSchedulerDetail job : jobSchedulerDetails) {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getProcessGroupHeaderId().toString());
            try {
                if (jobScheduler.getScheduler().checkExists(jobKey)) {
                    jobScheduler.getScheduler().pauseJob(jobKey);
                    job.setActiveFlag(false);
                }
            } catch (SchedulerException e) {
                throw new RuntimeException(
                        "Failed to pause job: " + jobKey + " - " + e.getMessage(), e);
            }
        }
        updateAll(jobSchedulerDetails);
        return "Paused JOBGroup with Id: "+processGroupHeaderId;
    }

    @Override
    public String resumeJOBGroup(Long processGroupHeaderId) {
        List<JOBSchedulerDetail> jobSchedulerDetails = findByProcessGroupHeaderId(processGroupHeaderId);
        for (JOBSchedulerDetail job : jobSchedulerDetails) {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getProcessGroupHeaderId().toString());
            try {
                if (jobScheduler.getScheduler().checkExists(jobKey)) {
                    jobScheduler.getScheduler().resumeJob(jobKey);
                    job.setActiveFlag(true);
                }
            } catch (SchedulerException e) {
                throw new RuntimeException(
                        "Failed to resume job: " + jobKey + " - " + e.getMessage(), e);
            }
        }
        updateAll(jobSchedulerDetails);
        return "Resumed JOBGroup with Id: "+processGroupHeaderId;
    }

    @Override
    public String killJOBGroup(Long processGroupHeaderId) {
        List<JOBSchedulerDetail> jobSchedulerDetails = findByProcessGroupHeaderId(processGroupHeaderId);
        List<JobKey> jobKeys = getJobKeysList(jobSchedulerDetails);
        try {
            if (!jobKeys.isEmpty()) jobScheduler.getScheduler().deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to delete job group jobs", e);
        }
        deleteAllById(jobSchedulerDetails.stream()
                .map(JOBSchedulerDetail::getId)
                .collect(Collectors.toList()));
        return "Killed JOBGroup with Id: "+processGroupHeaderId;
    }

    @Override
    public String pauseAll() {
        try {
            jobScheduler.getScheduler().pauseAll();
            List<JOBSchedulerDetail> jobSchedulerDetails = readAllJobs();
            jobSchedulerDetails.forEach(job -> job.setActiveFlag(false));
            updateAll(jobSchedulerDetails);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to pause all jobs", e);
        }
        return "Paused all the jobs";
    }

    @Override
    public String resumeAll() {
        try {
            jobScheduler.getScheduler().resumeAll();
            List<JOBSchedulerDetail> jobSchedulerDetails = readAllJobs();
            jobSchedulerDetails.forEach(job-> job.setActiveFlag(true));
            updateAll(jobSchedulerDetails);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to resume all jobs: " + e);
        }
        return "Resumed all the jobs";
    }

    @Override
    public String killAll() {
        List<JOBSchedulerDetail> jobSchedulerDetails = readAllJobs();
        List<JobKey> jobKeys = getJobKeysList(jobSchedulerDetails);
        try {
            if (!jobKeys.isEmpty()) jobScheduler.getScheduler().deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to delete all jobs", e);
        }
        deleteAllById(jobSchedulerDetails.stream()
                .map(JOBSchedulerDetail::getId)
                .collect(Collectors.toList()));
        return "Killed all the jobs";
    }

    @Override
    public List<JobKey> getJobKeysList(List<JOBSchedulerDetail> jobSchedulerDetails) {
        return jobSchedulerDetails.stream()
                .map(job -> JobKey.jobKey(job.getJobName(), job.getProcessGroupHeaderId().toString()))
                .filter(jobKey -> {
                    try {
                        return jobScheduler.getScheduler().checkExists(jobKey);
                    } catch (SchedulerException e) {
                        throw new RuntimeException("Failed to check job existence: " + jobKey, e);
                    }
                }).toList();
    }

    public Map<String, Map<String, String>> getJobGroupsWithRunningStatus() {
        Map<String, Map<String, String>> groupJobsStatus = new HashMap<>();
        try {
            for (String groupName : jobScheduler.getScheduler().getJobGroupNames()) {
                Map<String, String> jobStatusMap = new HashMap<>();
                Set<JobKey> jobKeys = jobScheduler.getScheduler().getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                for (JobKey jobKey : jobKeys) {
                    List<? extends Trigger> triggers = jobScheduler.getScheduler().getTriggersOfJob(jobKey);
                    // Take first trigger as representative (or handle multiple triggers)
                    String status = triggers.stream()
                            .map(trigger -> {
                                try {
                                    return jobScheduler.getScheduler().getTriggerState(trigger.getKey());
                                } catch (SchedulerException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .map(Enum::name).findFirst().orElse("UNKNOWN");
                    jobStatusMap.put(jobKey.getName(), status);
                }
                groupJobsStatus.put(groupName, jobStatusMap);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to fetch jobs and running status: " + e.getMessage(), e);
        }
        return groupJobsStatus;
    }

    public Map<String, SchedulerJobExecutionMonitorVO> getAllProcessData() {
        return schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap();
    }

    @Override
    public Boolean isJobSchedulerThreadAlive(String threadName) {
        try {
            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.getName().equals(threadName) && thread.isAlive()) {
                    return Boolean.TRUE;
                }
            }
        } catch (NullPointerException exp) {
            log.error(exp.getMessage(), exp);
            throw new RuntimeException("An exception occurred with message: "+exp.getMessage());
        }
        return Boolean.FALSE;
    }

    @Override
    public Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(JOBSchedulerDetail jobSchedulerDetail) {
        Trigger quartzTrigger = null;
        try {
            quartzTrigger = jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName()));
            if (isNull(quartzTrigger)) {
                createQuartzTrigger(jobSchedulerDetail);
                quartzTrigger = jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName()));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("An exception occurred with message: "+ex.getMessage());
        }
        return new ImmutablePair<Date, Date>(quartzTrigger.getPreviousFireTime(), quartzTrigger.getNextFireTime());
    }

    @Override
    public Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(String triggerName, String triggerGroupName) {
        Trigger quartzTrigger = null;
        try {
            quartzTrigger = jobScheduler.getScheduler().getTrigger(triggerKey(triggerName, triggerGroupName));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("An exception occurred with message: "+ex.getMessage());
        }
        return new ImmutablePair<Date, Date>(quartzTrigger.getPreviousFireTime(), quartzTrigger.getNextFireTime());
    }

    @Override
    public Integer doStart() {
        return 0;
    }

    @Override
    public Integer doStandBy() {
        return 0;
    }

    @Override
    public void fireStopCompletionEvent(Boolean var1, String var2) {

    }

}


