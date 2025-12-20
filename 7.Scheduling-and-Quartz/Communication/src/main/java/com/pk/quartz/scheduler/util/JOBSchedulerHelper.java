package com.pk.quartz.scheduler.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pk.development.util.ValidatorUtils;
import com.pk.quartz.scheduler.constants.SchedulerStatusEnum;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.pk.quartz.scheduler.domainobject.SchedulerProcessErrorLogDetail;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;

import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("jobSchedulerHelper")
public class JOBSchedulerHelper {
	
	public JOBSchedulerExecutionLog prepareJobExectutionLog(String triggerName, String jobMetaInformation) {
		JOBSchedulerExecutionLog schedulerExecutionLog = new JOBSchedulerExecutionLog();
		schedulerExecutionLog.setTriggerName(triggerName);
		schedulerExecutionLog.setJobMetaInformation(jobMetaInformation);
		schedulerExecutionLog.setExecutionStartTime(LocalDateTime.now());
		schedulerExecutionLog.setCreationTimeStamp(LocalDateTime.now());
		schedulerExecutionLog.setMachineName(this.getMachineName());
		return schedulerExecutionLog;
	}


	public synchronized SchedulerJobExecutionMonitorVO updateDataForTriggerExecution(
			SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO, Character executionStatus,
			Date previousExecutionTime, Date nextExecutionTime, int jobState) {
		if (ValidatorUtils.notNull(previousExecutionTime)) {
			schedulerJobExecutionMonitorVO.setPreviousExecutionTime(previousExecutionTime);
		}

		if (ValidatorUtils.notNull(executionStatus)) {
			if (ValidatorUtils.notNull(nextExecutionTime)
					&& !SchedulerStatusEnum.PAUSED.equalsValue(executionStatus)) {
				schedulerJobExecutionMonitorVO.setNextExecutionTime(nextExecutionTime);
			}

			if (SchedulerStatusEnum.RUNNING.equalsValue(executionStatus)) {
				schedulerJobExecutionMonitorVO.intializeProcessStartTime();
			} else if (!SchedulerStatusEnum.COMPLETED.equalsValue(executionStatus)
					&& !SchedulerStatusEnum.FAILED.equalsValue(executionStatus)) {
				if (SchedulerStatusEnum.PAUSED.equalsValue(executionStatus)) {
					schedulerJobExecutionMonitorVO.setNextExecutionTime((Date) null);
				}
			} else {
				schedulerJobExecutionMonitorVO.setProcessEndTime(new Date());
			}

			schedulerJobExecutionMonitorVO.setCurrentStatus(executionStatus);
		}

		if (0 == jobState) {
			schedulerJobExecutionMonitorVO.setNextExecutionTime((Date) null);
		} else {
			schedulerJobExecutionMonitorVO.setNextExecutionTime(nextExecutionTime);
		}

		schedulerJobExecutionMonitorVO.setJobState(jobState);
		//schedulerJobExecutionMonitorVO.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName());
		return schedulerJobExecutionMonitorVO;
	}

	public String prepareKeyForJobExecution(String jobGroupName, String jobName, String triggerGroupName,
			String triggerName) {
		StringBuilder keyForJobExecution = new StringBuilder();
		keyForJobExecution.append(jobGroupName)
				.append("#").append(jobName)
				.append("#").append(triggerGroupName)
				.append("#").append(triggerName);
		return keyForJobExecution.toString();
	}

	public List<SchedulerProcessErrorLogDetail> prepareProcessErrorLogEntity(Long schedulerProcessId,
			String triggerName, String jobMetaInformation, Exception theException) {
		StringWriter theStringWriter = new StringWriter();
		PrintWriter thePrintWriter = new PrintWriter(theStringWriter, true);
		List<SchedulerProcessErrorLogDetail> theNEOSchedulerProcessErrorLogDetails = new ArrayList();
	

		ExceptionUtils.printRootCauseStackTrace(theException, thePrintWriter);
		
		SchedulerProcessErrorLogDetail theNEOSchedulerProcessErrorLogDetail = (new SchedulerProcessErrorLogDetail.Builder())
				//.processBusinessDate(this.requestServicingContext.getBusinessDate())
				//.errorMessageId(theException.get)
				.errorDescription(theException.getMessage())
				//.errorMessageparameters(Arrays.toString(theMessage.getMessageArguments())).errorType(0L)
				.exceptionStackDescription(ExceptionUtils.getStackTrace(theException)
						+ ", {printRootCauseStackTrace:" + theStringWriter.toString() + "}")
				.schedulerProcessId(schedulerProcessId).jobMetaInformation(jobMetaInformation).build();
		theNEOSchedulerProcessErrorLogDetail.setCreationTimeStamp(LocalDateTime.now());
		theNEOSchedulerProcessErrorLogDetails.add(theNEOSchedulerProcessErrorLogDetail);

		return theNEOSchedulerProcessErrorLogDetails;
	}


	private String getMachineName() {
		String machineName = "UnknownHost";

		try {
			machineName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException var3) {
			log.error(var3.getMessage(), var3);
		}

		return machineName;
	}
}