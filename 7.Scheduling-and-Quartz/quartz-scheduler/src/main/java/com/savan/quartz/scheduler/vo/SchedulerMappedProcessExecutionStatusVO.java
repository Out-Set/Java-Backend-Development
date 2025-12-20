package com.savan.quartz.scheduler.vo;

import java.io.Serializable;
import java.util.Date;

import com.savan.quartz.scheduler.constants.SchedulerStatusEnum;
import com.savan.quartz.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
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

}
