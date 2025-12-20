package com.pk.quartz.scheduler.domainobject;

import java.io.Serializable;

public class JOBSchedulerDetailVO implements Serializable {
	private static final long serialVersionUID = -6732441720335721003L;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private boolean activeFlag;

	public JOBSchedulerDetailVO() {
	}

	public JOBSchedulerDetailVO(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		this.jobName = jobName;
		this.jobGroupName = jobGroupName;
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
	}

	public JOBSchedulerDetailVO(String jobName, Long jobGroupName, String triggerName, String triggerGroupName) {
		this.jobName = jobName;
		this.jobGroupName = String.valueOf(jobGroupName);
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
	}

	public JOBSchedulerDetailVO(Long jobGroupName, String triggerName, String triggerGroupName) {
		this.jobGroupName = String.valueOf(jobGroupName);
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
	}

	public JOBSchedulerDetailVO(String jobName, Long jobGroupName, String triggerName, String triggerGroupName,
			boolean activeFlag) {
		this.jobName = jobName;
		this.jobGroupName = String.valueOf(jobGroupName);
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
		this.activeFlag = activeFlag;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return this.jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroupName() {
		return this.triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public boolean isActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
}