package com.pk.quartz.scheduler.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;

import com.pk.development.util.ValidatorUtils;
import com.pk.quartz.scheduler.constants.SchedulerStatusEnum;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.serviceinterface.IQuartzJOBSchedulerService;
import com.pk.quartz.scheduler.serviceinterface.ITriggerListener;
import com.pk.quartz.scheduler.util.JOBSchedulerHelper;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO.SchedulerJobExecutionMonitorVOBuilder;
import com.pk.quartz.scheduler.vo.SchedulerMappedProcessExecutionStatusVO;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

@Slf4j
public class TriggerListener implements ITriggerListener {
	private String name;
	@Inject
	private JOBSchedulerHelper jobSchedulerHelper;

    @Lazy
	@Inject
	@Named("quartzJobSchedulerService")
	private IQuartzJOBSchedulerService jobSchedulerService;
	@Inject
	@Named("schedulerExecutionStateData")
	private SchedulerExecutionStateData schedulerExecutionStateData;

	public TriggerListener() {
		this.name = "APIFlowTriggerListener";
	}

	public TriggerListener(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}


	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.info("Trigger Fired....");
		try {
			List<JOBSchedulerDetail> theJOBSchedulerDetails = jobSchedulerService.getJOBSchedulerDetailByJOBGroupAndName(trigger.getJobKey().getGroup(), trigger.getJobKey().getName());
			String compositeJobAndTriggerKey = jobSchedulerHelper.prepareKeyForJobExecution(trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), trigger.getKey().getGroup(),trigger.getKey().getName());
			SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);

			log.info("Trigger fired-1: {}", schedulerJobExecutionMonitorVO);
			if (ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
				schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder().setJobGroupName(trigger.getJobKey().getGroup())
								.setJobName(trigger.getJobKey().getName())
								.setTriggerGroupName(trigger.getKey().getGroup())
								.setTriggerName(trigger.getKey().getName())
								.setPreviousExecutionTime(trigger.getPreviousFireTime())
								.setNextExecutionTime(trigger.getNextFireTime()).setProcessStartTime(new Date())
								.setCurrentStatus(SchedulerStatusEnum.RUNNING.getEnumValue())
								/*.setJobState(((JOBSchedulerDetail) theJOBSchedulerDetails.get(0)).isActiveFlag()
										? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
										: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())
								.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
								.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())*/
								.setMappedProcessExecutionStatusList(new LinkedList<>())
								.setThreadName(Thread.currentThread().getName())
								//.setModule(ProductInformationLoader.getProductCode())
								.setModule("APIFlow").build();
				schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
			} else if (SchedulerStatusEnum.RUNNING.equalsValue(schedulerJobExecutionMonitorVO.getCurrentStatus())) {
				log.error("Another instance of JOB {} is in progress.",	context.getTrigger().getKey().getName());
			} else {
				schedulerJobExecutionMonitorVO.setThreadName(Thread.currentThread().getName());
				SchedulerJobExecutionMonitorVO localSchedulerJobExecutionMonitorVO = jobSchedulerHelper.updateDataForTriggerExecution(schedulerJobExecutionMonitorVO,
								SchedulerStatusEnum.RUNNING.getEnumValue(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), schedulerJobExecutionMonitorVO.getJobState());
				localSchedulerJobExecutionMonitorVO.setRecentExecutionSequence(-1);
				localSchedulerJobExecutionMonitorVO.setJobExecutionLogId((Long) null);
				//this.neoSchedulerCacheService.aware(compositeJobAndTriggerKey, localSchedulerJobExecutionMonitorVO);
			}
			log.info("Trigger fired-2: {}", schedulerJobExecutionMonitorVO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		log.info("Veto Job Execution....");
		String compositeJobAndTriggerKey = jobSchedulerHelper.prepareKeyForJobExecution(trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), trigger.getKey().getGroup(),trigger.getKey().getName());
		SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);
		boolean veto =  ValidatorUtils.notNull(schedulerJobExecutionMonitorVO)
				&& isAnyInstanceOfGivenSchedulerIsInProgress(schedulerJobExecutionMonitorVO, SchedulerStatusEnum.RUNNING.getEnumValue())
				|| isAnyInstanceOfGivenSchedulerIsInProgress(schedulerJobExecutionMonitorVO, SchedulerStatusEnum.QUEUED.getEnumValue());
		log.info("Veto Job Execution.... :{}",veto);
		return veto;
	}

	public void triggerMisfired(Trigger trigger) {
		log.info("triggerMisfired....");
		try {
			String compositeJobAndTriggerKey = jobSchedulerHelper.prepareKeyForJobExecution(trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), trigger.getKey().getGroup(),trigger.getKey().getName());
			SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);
			if (ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
				schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder().setJobGroupName(trigger.getJobKey().getGroup())
					.setJobName(trigger.getJobKey().getName())
					.setTriggerGroupName(trigger.getKey().getGroup())
					.setTriggerName(trigger.getKey().getName())
					.setPreviousExecutionTime(trigger.getPreviousFireTime())
					.setNextExecutionTime(trigger.getNextFireTime())
					.setProcessStartTime(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getProcessStartTime())
					.setCurrentStatus(SchedulerStatusEnum.RUNNING.getEnumValue())
					/*.setJobState((jobSchedulerService.getJOBSchedulerDetailByJOBGroupAndName(trigger.getJobKey().getGroup(),
									trigger.getJobKey().getName())
							.get(0)).isActiveFlag()? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
									: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())
					.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
					.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())*/
					.setMappedProcessExecutionStatusList(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey,new SchedulerJobExecutionMonitorVO()).getMappedProcessExecutionStatusList())
					//.setModule(ProductInformationLoader.getProductCode())
					.setModule("APFlow").build();
				schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
			} else {
				jobSchedulerHelper.updateDataForTriggerExecution(schedulerJobExecutionMonitorVO,
						SchedulerStatusEnum.RUNNING.getEnumValue(), trigger.getPreviousFireTime(),
						trigger.getNextFireTime(), schedulerJobExecutionMonitorVO.getJobState());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		log.info("Trigger completed....");
		try {
			String compositeJobAndTriggerKey = jobSchedulerHelper.prepareKeyForJobExecution(trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), trigger.getKey().getGroup(),	trigger.getKey().getName());
			SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);

			if (!SchedulerStatusEnum.QUEUED.equalsValue(schedulerJobExecutionMonitorVO.getCurrentStatus())
					&& !SchedulerStatusEnum.BLOCKED.equalsValue(schedulerJobExecutionMonitorVO.getCurrentStatus())) {
				if (ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
					schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder()
							.setJobGroupName(trigger.getJobKey().getGroup()).setJobName(trigger.getJobKey().getName())
							.setTriggerGroupName(trigger.getKey().getGroup()).setTriggerName(trigger.getKey().getName())
							.setPreviousExecutionTime(trigger.getPreviousFireTime())
							.setNextExecutionTime(trigger.getNextFireTime())
							.setProcessStartTime((schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO())).getProcessStartTime())
							/*.setJobState((jobSchedulerService
									.getJOBSchedulerDetailByJOBGroupAndName(trigger.getJobKey().getGroup(),
											trigger.getJobKey().getName())
									.get(0)).isActiveFlag()
											? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
											: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())
							.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
							.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())*/
							.setMappedProcessExecutionStatusList(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getMappedProcessExecutionStatusList())
							.setRecentExecutionSequence(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getRecentExecutionSequence())
							.setJobExecutionLogId(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
									.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getJobExecutionLogId())
							//.setModule(ProductInformationLoader.getProductCode())
							.setModule("APIFlow").build();
					schedulerJobExecutionMonitorVO.setCurrentStatus(decideStatusAfterCompletionOfTrigger(schedulerJobExecutionMonitorVO));
					schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
				} else {

					jobSchedulerHelper.updateDataForTriggerExecution(schedulerJobExecutionMonitorVO, decideStatusAfterCompletionOfTrigger(schedulerJobExecutionMonitorVO),
							trigger.getPreviousFireTime(), trigger.getNextFireTime(), schedulerJobExecutionMonitorVO.getJobState());
				}
				/** Commented due to cache configuration not enabled, will be applicable when cache enabled*/
				//schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap().remove(compositeJobAndTriggerKey);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	private Boolean isAnyInstanceOfGivenSchedulerIsInProgress(SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorTempVO, Character schedulerStatusEnum) {
		return ValidatorUtils.notNull(schedulerJobExecutionMonitorTempVO)
				&& schedulerStatusEnum.equals(schedulerJobExecutionMonitorTempVO.getCurrentStatus())
				&& jobSchedulerService.isJobSchedulerThreadAlive(schedulerJobExecutionMonitorTempVO.getThreadName())
				&& !schedulerJobExecutionMonitorTempVO.getThreadName().equals(Thread.currentThread().getName());
	}

	private Character decideStatusAfterCompletionOfTrigger(SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO) {
		List<SchedulerMappedProcessExecutionStatusVO> failedJobSchedulerExecutionMappingDetails = schedulerJobExecutionMonitorVO
				.getMappedProcessExecutionStatusList().stream().filter((jobSchedulerExecutionMappingDetail) -> {
					return SchedulerStatusEnum.FAILED.equalsValue(jobSchedulerExecutionMappingDetail.getCurrentStatus());
				}).collect(Collectors.toList());
		return failedJobSchedulerExecutionMappingDetails.isEmpty()? SchedulerStatusEnum.COMPLETED.getEnumValue() : SchedulerStatusEnum.COMPLETED_WITH_EXCEPTION.getEnumValue();
	}
}
