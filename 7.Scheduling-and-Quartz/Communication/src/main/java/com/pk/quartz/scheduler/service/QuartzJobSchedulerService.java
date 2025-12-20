package com.pk.quartz.scheduler.service;

import static com.pk.development.util.ValidatorUtils.isNull;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pk.development.util.ValidatorUtils;
import com.pk.quartz.scheduler.businessobject.IJOBSchedulerBusinessObject;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.pk.quartz.scheduler.jobs.GenericJOBExecutor;
import com.pk.quartz.scheduler.serviceinterface.IQuartzJOBSchedulerService;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Named("quartzJobSchedulerService")
public class QuartzJobSchedulerService implements IQuartzJOBSchedulerService {

	@Inject
	private SchedulerFactoryBean jobScheduler;
	@Inject
	@Named("schedulerExecutionStateData")
	private SchedulerExecutionStateData schedulerExecutionStateData;
	@Inject
	@Named("jobSchedulerBusinessObject")
	private IJOBSchedulerBusinessObject jobSchedulerBusinessObject;

    /**
     * Creates a new Quartz trigger with the given job details.
     * @return 
     */
    public Integer createQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) {
    	Integer actionStatus = 0;
        JobDetail job = newJob(GenericJOBExecutor.class)
                .withIdentity(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId())
                .storeDurably()
                .requestRecovery(false)
                .usingJobData(prepareJobDataForChainedExecution(jobSchedulerDetail))
                .build();

        Trigger trigger = prepareCronTrigger(jobSchedulerDetail);
        
        try {
			jobScheduler.getScheduler().scheduleJob(job, trigger);
			if (!jobSchedulerDetail.isActiveFlag()) {
				jobScheduler.getScheduler().pauseJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId()));
			}

			return actionStatus;
		} catch (SchedulerException se) {
			log.error(se.getMessage(), se);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
        
        return actionStatus;
    }
    
    /**
     * Updates the Quartz trigger if it exists; otherwise creates a new one.
     */
    public Integer updateQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) {
        Integer actionStatus = 0;
        try {
            // Check if the trigger already exists
            if (isTriggerAlreadyConfigured(jobSchedulerDetail)) {
                try {
                    // Reschedule the trigger with the new cron expression
                    jobScheduler.getScheduler().rescheduleJob(
                            triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName()),
                            prepareCronTrigger(jobSchedulerDetail)
                    );
                    // Update job data if needed
                    jobScheduler.getScheduler().getJobDetail(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId()))
                            .getJobBuilder()
                            .usingJobData(prepareJobDataForChainedExecution(jobSchedulerDetail));

                    // Pause or resume job based on active flag
                    if (!jobSchedulerDetail.isActiveFlag()) {
                        jobScheduler.getScheduler().pauseJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId()));
                    } else {
                        jobScheduler.getScheduler().resumeJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId()));
                    }

                } catch (Exception e) {
                	log.error(e.getMessage(),e);
                }
            } else {
                // Trigger doesn't exist, create a new one
                createQuartzTrigger(jobSchedulerDetail);
            }

            // If next fire time is null, create the trigger again
            if (jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())) == null ||
                jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())).getNextFireTime() == null) {
                createQuartzTrigger(jobSchedulerDetail);
            }

        } catch (SchedulerException e) {
            actionStatus = 1;
            log.error(e.getMessage(),e);
        }
        return actionStatus;
    }

    /**
     * Checks if the trigger is already configured.
     */
    private boolean isTriggerAlreadyConfigured(JOBSchedulerDetail jobSchedulerDetail) throws SchedulerException {
        TriggerKey triggerKey = triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName());
        return jobScheduler.getScheduler().checkExists(triggerKey);
    }
    
    /**
     * Prepares a cron trigger based on the job details.
     */
    private Trigger prepareCronTrigger(JOBSchedulerDetail jobSchedulerDetail) {
        return newTrigger()
                .withIdentity(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())
                .withSchedule(cronSchedule(jobSchedulerDetail.getCronExpression()))
                .build();
    }

    private JobDataMap prepareJobDataForChainedExecution(JOBSchedulerDetail jobSchedulerDetail) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobSchedulerDetail", jobSchedulerDetail);
        return jobDataMap;
    }
    
    public Integer deleteQuartzTrigger(JOBSchedulerDetail jobSchedulerDetail) {
        Integer actionStatus = 0;
        try {
           if (ValidatorUtils.notNull(jobScheduler.getScheduler().getTrigger(triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())))) {
        	   jobScheduler.getScheduler().unscheduleJob(triggerKey(jobSchedulerDetail.getTriggerName()));
        	   jobScheduler.getScheduler().deleteJob(jobKey(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupId()));
           }
        } catch (SchedulerException var4) {
           log.error(var4.getMessage(), var4);
        }
        return actionStatus;
     }
    
    public void pauseJob(String jobName, String group) throws SchedulerException {
        JobKey jobKey = jobKey(jobName, group);
        if (jobScheduler.getScheduler().checkExists(jobKey)) {
            jobScheduler.getScheduler().pauseJob(jobKey);
            System.out.println("Job paused: " + jobName + " in group: " + group);
        } else {
            throw new SchedulerException("Job does not exist: " + jobName + " in group: " + group);
        }
    }

    public void deleteJob(String triggerName, String triggerGroupName) throws SchedulerException {
        JobKey jobKey = jobKey(triggerName, triggerGroupName);
        if (jobScheduler.getScheduler().checkExists(jobKey)) {
            jobScheduler.getScheduler().deleteJob(jobKey);
            System.out.println("Job deleted: " + triggerName + " in group: " + triggerGroupName);
        } else {
            throw new SchedulerException("Job does not exist: " + triggerName + " in group: " + triggerGroupName);
        }
    }
    
    public void resumeJob(String triggerName, String triggerGroupName) throws SchedulerException {
        JobKey jobKey = jobKey(triggerName, triggerGroupName);
        if (jobScheduler.getScheduler().checkExists(jobKey)) {
            jobScheduler.getScheduler().resumeJob(jobKey);
            System.out.println("Job resumed: " + triggerName + " in group: " + triggerGroupName);
        } else {
            throw new SchedulerException("Job does not exist: " + triggerName + " in group: " + triggerGroupName);
        }
    }
    
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public JOBSchedulerExecutionLog createJobExectutionLog(JOBSchedulerExecutionLog schedulerExecutionLog) {
		return jobSchedulerBusinessObject.createJobExectutionLog(schedulerExecutionLog);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public JOBSchedulerExecutionLog updateJobExectutionLog(Long jobLogId) {
		JOBSchedulerExecutionLog schedulerExecutionLog = jobSchedulerBusinessObject.findById(jobLogId, JOBSchedulerExecutionLog.class);
		schedulerExecutionLog.setLastUpdatedTimeStamp(LocalDateTime.now());
		schedulerExecutionLog.setExecutionEndTime(LocalDateTime.now());
		log.info("Execution log updated for: {}",schedulerExecutionLog);
		return schedulerExecutionLog;
	}


	public Map<String, SchedulerJobExecutionMonitorVO> getAllProcessData() {
		return schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap();
	}

	@Override
	public List<JOBSchedulerDetail> getNeoJobsByTriggerName(String var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JOBSchedulerDetail> getRegisteredJobs(Long var1) {
		// TODO Auto-generated method stub
		return null;
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
		} catch (SchedulerException sex) {
			log.error(sex.getMessage(), sex);
		}

		return new ImmutablePair<Date, Date>(quartzTrigger.getPreviousFireTime(), quartzTrigger.getNextFireTime());
	}

	@Override
	public List<JOBSchedulerDetail> getJobSchedulerProcessByProcessGroup(Long var1, List<Integer> var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JOBSchedulerDetail getJOBSchedulerDetailByExecutionSequence(Long var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> getAllMappedProcessIdAccrossAllProcessGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JOBSchedulerDetail getActiveScheduledJobByJobName(String var1, List<Integer> var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pauseJOBGroup(String var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer resumeJOBGroup(String var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer killJOBGroup(String var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<JOBSchedulerDetail>> pauseAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<JOBSchedulerDetail>> resumeAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<JOBSchedulerDetail>> killAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String var1, String var2,
			List<Integer> var3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Date, Date> getQuartzTriggerPreviousAndNextFireTime(String var1, String var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer doStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer doStandBy() {
		// TODO Auto-generated method stub
		return null;
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
		}

	    return Boolean.FALSE;
	}

	

	@Override
	public void fireStopCompletionEvent(Boolean var1, String var2) {
		// TODO Auto-generated method stub
		
	}
}

