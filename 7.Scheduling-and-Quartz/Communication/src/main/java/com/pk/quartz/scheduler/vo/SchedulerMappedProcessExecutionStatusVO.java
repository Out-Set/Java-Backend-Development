package com.pk.quartz.scheduler.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.pk.development.util.ValidatorUtils;
import com.pk.quartz.scheduler.constants.SchedulerStatusEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchedulerMappedProcessExecutionStatusVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String processDisplayName = "";
	private String methodToExecute = "";
	private Character currentStatus = ' ';
	private Date previousExecutionTime = null;
	private Date nextExecutionTime = null;
	private Date processStartTime = null;
	private Date processEndTime = null;
	private Long nodeId = 0L;
	private Long tenantId = 0L;
	private Integer executionSequence = -1;

	public String getProcessDisplayName() {
		return this.processDisplayName;
	}

	public void setProcessDisplayName(String processDisplayName) {
		this.processDisplayName = processDisplayName;
	}

	public String getMethodToExecute() {
		return this.methodToExecute;
	}

	public void setMethodToExecute(String methodToExecute) {
		this.methodToExecute = methodToExecute;
	}

	public Character getCurrentStatus() {
		return this.currentStatus;
	}

	public void setCurrentStatus(Character currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getPreviousExecutionTime() {
		return this.previousExecutionTime;
	}

	public void setPreviousExecutionTime(Date previousExecutionTime) {
		this.previousExecutionTime = previousExecutionTime;
	}

	public Date getNextExecutionTime() {
		return this.nextExecutionTime;
	}

	public void setNextExecutionTime(Date nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}

	public Date getProcessStartTime() {
		return this.processStartTime;
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}

	public Date getProcessEndTime() {
		return this.processEndTime;
	}

	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}

	public Integer getExecutionSequence() {
		return this.executionSequence;
	}

	public void setExecutionSequence(Integer executionSequence) {
		this.executionSequence = executionSequence;
	}

	public static long getSerialversionuid() {
		return 1L;
	}

	public String getExecutingSince() {
		return SchedulerStatusEnum.RUNNING.equalsValue(this.getCurrentStatus())
				? this.getDaysHrsSecMin((new Date()).getTime() - this.getProcessStartTime().getTime())
				: "";
	}

	public String getTotalExecutionTime() {
		if (SchedulerStatusEnum.COMPLETED.equalsValue(this.getCurrentStatus())) {
			return !ValidatorUtils.isNull(this.getProcessEndTime())
					&& !ValidatorUtils.isNull(this.getProcessStartTime())
							? this.getDaysHrsSecMin(
									this.getProcessEndTime().getTime() - this.getProcessStartTime().getTime())
							: "";
		} else {
			return "";
		}
	}

	public String getCurrentStatusDescription() {
		return SchedulerStatusEnum.getEnumFromValue(this.getCurrentStatus()).getDescription();
	}

	public void markCompletionSuccess() {
		this.setProcessEndTime(new Date());
		this.setCurrentStatus(SchedulerStatusEnum.COMPLETED.getEnumValue());
	}

	public void markCompletionWithFault() {
		this.setProcessEndTime(new Date());
		this.setCurrentStatus(SchedulerStatusEnum.FAILED.getEnumValue());
	}

	public void markExecutionVetoed() {
		this.setCurrentStatus(SchedulerStatusEnum.VETOED.getEnumValue());
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
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return "" + token;
		}
	}

	public void intializeProcessStartTime() {
		this.setProcessStartTime(new Date());
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
}
