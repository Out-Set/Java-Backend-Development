package com.savan.quartz.scheduler.vo;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JOBSchedulerDetailVO implements Serializable {
	private static final long serialVersionUID = -6732441720335721003L;
    private Long id;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private boolean activeFlag;
    private String cronExpression;
    private Integer runOnHoliday;
    private String environment;

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
}