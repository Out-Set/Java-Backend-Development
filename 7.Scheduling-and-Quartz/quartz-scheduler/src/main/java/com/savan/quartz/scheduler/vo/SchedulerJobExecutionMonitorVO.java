package com.savan.quartz.scheduler.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.savan.quartz.scheduler.constants.SchedulerStatusEnum;
import com.savan.quartz.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import lombok.ToString;

@Getter
@Setter
@ToString
public class SchedulerJobExecutionMonitorVO implements Serializable {
	private static final long serialVersionUID = 8429529831159931556L;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private String processGroup;
	private String methodToExecute;
	private Character currentStatus;
	private Date previousExecutionTime;
	private Date nextExecutionTime;
	private Date processStartTime;
	private Date processEndTime;
	private String nodeId;
	private Long tenantId;
	private int jobState;
	private Boolean isExecutionToBeVetoed;
	private List<SchedulerMappedProcessExecutionStatusVO> mappedProcessExecutionStatusList;
	private String threadName;
	private int recentExecutionSequence;
	private Long jobExecutionLogId;
	private String module;

	public SchedulerJobExecutionMonitorVO() {
		this.mappedProcessExecutionStatusList = null;
		this.recentExecutionSequence = -1;
		this.jobName = "";
		this.jobGroupName = "";
		this.triggerName = "";
		this.triggerGroupName = "";
		this.processGroup = "";
		this.methodToExecute = "";
		this.currentStatus = ' ';
		this.previousExecutionTime = null;
		this.nextExecutionTime = null;
		this.processStartTime = new Date();
		this.nodeId = "";
		this.tenantId = 0L;
		this.mappedProcessExecutionStatusList = new LinkedList<>();
		this.isExecutionToBeVetoed = Boolean.FALSE;
		this.threadName = "";
		this.recentExecutionSequence = -1;
		this.jobExecutionLogId = null;
		this.module = "";
	}

    /*public Long getJobGroupLongValue() {
		return !"".equals(this.getJobGroupName()) && this.getJobGroupName() != null
				? Long.valueOf(this.getJobGroupName())
				: null;
	}

	public Long getJobLongValue() {
		return !"".equals(this.getJobName()) && this.getJobName() != null ? Long.valueOf(this.getJobName()) : null;
	}*/

    public String getCurrentStatusDescription() {
		return SchedulerStatusEnum.getEnumFromValue(this.getCurrentStatus()).getDescription();
	}

    public String getExecutingSince() {
		return SchedulerStatusEnum.RUNNING.equalsValue(this.getCurrentStatus())
				? this.getDaysHrsSecMin((new Date()).getTime() - this.getProcessStartTime().getTime())
				: "";
	}

    public String getTotalExecutionTime() {
		return (SchedulerStatusEnum.COMPLETED.equalsValue(this.getCurrentStatus())
				|| SchedulerStatusEnum.COMPLETED_WITH_EXCEPTION.equalsValue(this.getCurrentStatus()))
				&& ValidatorUtils.notNull(this.getProcessEndTime())
				&& ValidatorUtils.notNull(this.getProcessStartTime())
						? this.getDaysHrsSecMin(
								this.getProcessEndTime().getTime() - this.getProcessStartTime().getTime())
						: "";
	}

    protected String getDaysHrsSecMin(Long miliSec) {
		Long miliSecTmp = Math.abs(miliSec);
		StringBuilder timeElapsedStr = new StringBuilder();
		miliSecTmp = miliSecTmp / 1000L;
		Long days = miliSecTmp / 60L / 60L / 24L;
		miliSecTmp = miliSecTmp - days * 24L * 60L * 60L;
		Long hrs = miliSecTmp / 60L / 60L;
		miliSecTmp = miliSecTmp - hrs * 60L * 60L;
		Long min = miliSecTmp / 60L;
		miliSecTmp = miliSecTmp - min * 60L;
		if (days > 0L) {
			timeElapsedStr.append(this.lPad(days)).append(":");
		}

		timeElapsedStr.append(this.lPad(hrs)).append(":").append(this.lPad(min)).append(":")
				.append(this.lPad(miliSecTmp));
		return timeElapsedStr.toString();
	}

	private String lPad(Long token) {
		String zero = "0";

		try {
			return StringUtils.leftPad(token.toString(), 2, zero);
		} catch (Exception var4) {
			//BaseLoggers.exceptionLogger.error(var4.getMessage(), var4);
			return "" + token;
		}
	}

	public void intializeProcessStartTime() {
		this.setProcessStartTime(new Date());
	}

    private SchedulerJobExecutionMonitorVO(SchedulerJobExecutionMonitorVOBuilder schedulerJobExecutionMonitorVOBuilder) {
		this.recentExecutionSequence = -1;
		this.jobName = schedulerJobExecutionMonitorVOBuilder.jobName;
		this.jobGroupName = schedulerJobExecutionMonitorVOBuilder.jobGroupName;
		this.triggerName = schedulerJobExecutionMonitorVOBuilder.triggerName;
		this.triggerGroupName = schedulerJobExecutionMonitorVOBuilder.triggerGroupName;
		this.processGroup = schedulerJobExecutionMonitorVOBuilder.processGroup;
		this.methodToExecute = schedulerJobExecutionMonitorVOBuilder.methodToExecute;
		this.currentStatus = schedulerJobExecutionMonitorVOBuilder.currentStatus;
		this.previousExecutionTime = schedulerJobExecutionMonitorVOBuilder.previousExecutionTime;
		this.nextExecutionTime = schedulerJobExecutionMonitorVOBuilder.nextExecutionTime;
		this.processStartTime = schedulerJobExecutionMonitorVOBuilder.processStartTime;
		this.processEndTime = schedulerJobExecutionMonitorVOBuilder.processEndTime;
		this.nodeId = schedulerJobExecutionMonitorVOBuilder.nodeId;
		this.tenantId = schedulerJobExecutionMonitorVOBuilder.tenantId;
		this.jobState = schedulerJobExecutionMonitorVOBuilder.jobState;
		this.mappedProcessExecutionStatusList = schedulerJobExecutionMonitorVOBuilder.mappedProcessExecutionStatusList;
		this.isExecutionToBeVetoed = schedulerJobExecutionMonitorVOBuilder.isExecutionToBeVetoed;
		this.threadName = schedulerJobExecutionMonitorVOBuilder.threadName;
		this.recentExecutionSequence = schedulerJobExecutionMonitorVOBuilder.recentExecutionSequence;
		this.jobExecutionLogId = schedulerJobExecutionMonitorVOBuilder.jobExecutionLogId;
		this.module = schedulerJobExecutionMonitorVOBuilder.module;
	}

	public Boolean isOwner(String nodeName) {
		return this.getNodeId().equals(nodeName);
	}

	public static class SchedulerJobExecutionMonitorVOBuilder {
		private String jobName;
		private String jobGroupName;
		private String triggerName;
		private String triggerGroupName;
		private String processGroup;
		private String methodToExecute;
		private Character currentStatus;
		private Date previousExecutionTime;
		private Date nextExecutionTime;
		private Date processStartTime;
		private Date processEndTime;
		private String nodeId;
		private Long tenantId;
		private int jobState;
		private List<SchedulerMappedProcessExecutionStatusVO> mappedProcessExecutionStatusList;
		private Boolean isExecutionToBeVetoed;
		private String threadName;
		private int recentExecutionSequence = -1;
		private Long jobExecutionLogId;
		private String module;

		public SchedulerJobExecutionMonitorVOBuilder setJobName(String jobName) {
			this.jobName = jobName;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setJobGroupName(
				String jobGroupName) {
			this.jobGroupName = jobGroupName;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setTriggerName(String triggerName) {
			this.triggerName = triggerName;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setTriggerGroupName(
				String triggerGroupName) {
			this.triggerGroupName = triggerGroupName;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setProcessGroup(
				String processGroup) {
			this.processGroup = processGroup;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setMethodToExecute(
				String methodToExecute) {
			this.methodToExecute = methodToExecute;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setCurrentStatus(Character currentStatus) {
			this.currentStatus = currentStatus;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setPreviousExecutionTime(Date previousExecutionTime) {
			this.previousExecutionTime = ValidatorUtils.notNull(previousExecutionTime)
					? (Date) previousExecutionTime.clone()
					: null;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setNextExecutionTime(Date nextExecutionTime) {
			this.nextExecutionTime = ValidatorUtils.notNull(nextExecutionTime)
					? (Date) nextExecutionTime.clone()
					: null;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setProcessStartTime(Date processStartTime) {
			this.processStartTime = ValidatorUtils.notNull(processStartTime) ? (Date) processStartTime.clone() : null;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setProcessEndTime(Date processEndTime) {
			this.processEndTime = ValidatorUtils.notNull(processEndTime) ? (Date) processEndTime.clone() : null;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setNodeId(String nodeId) {
			this.nodeId = nodeId;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setTenantId(Long tenantId) {
			this.tenantId = tenantId;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setJobState(int jobState) {
			this.jobState = jobState;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setMappedProcessExecutionStatusList(
				List<SchedulerMappedProcessExecutionStatusVO> mappedProcessExecutionStatusList) {
			this.mappedProcessExecutionStatusList = mappedProcessExecutionStatusList;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setIsExecutionToBeVetoed(Boolean isExecutionToBeVetoed) {
			this.isExecutionToBeVetoed = isExecutionToBeVetoed;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setThreadName(String threadName) {
			this.threadName = threadName;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setRecentExecutionSequence(int recentExecutionSequence) {
			this.recentExecutionSequence = recentExecutionSequence;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setJobExecutionLogId(Long jobExecutionLogId) {
			this.jobExecutionLogId = jobExecutionLogId;
			return this;
		}

		public SchedulerJobExecutionMonitorVOBuilder setModule(String module) {
			this.module = module;
			return this;
		}

		public SchedulerJobExecutionMonitorVO build() {
			return new SchedulerJobExecutionMonitorVO(this);
		}
	}
}
