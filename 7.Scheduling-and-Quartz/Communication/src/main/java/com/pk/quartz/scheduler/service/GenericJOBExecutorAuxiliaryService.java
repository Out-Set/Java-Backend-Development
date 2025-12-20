package com.pk.quartz.scheduler.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.pk.development.util.ValidatorUtils;
import com.pk.quartz.scheduler.constants.SchedulerStatusEnum;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.pk.quartz.scheduler.serviceinterface.IGenericJOBExecutorAuxiliaryService;
import com.pk.quartz.scheduler.util.JOBSchedulerHelper;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO.SchedulerJobExecutionMonitorVOBuilder;
import com.pk.quartz.scheduler.vo.SchedulerMappedProcessExecutionStatusVO;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("genericJOBExecutorAuxiliaryService")
public class GenericJOBExecutorAuxiliaryService implements IGenericJOBExecutorAuxiliaryService {
	@Inject
	private BeanAccessHelper beanAccessHelper;
	@Inject
	@Named("schedulerExecutionStateData")
	private SchedulerExecutionStateData schedulerExecutionStateData;
	@Inject
	@Named("jobSchedulerHelper")
	private JOBSchedulerHelper jobSchedulerHelper;
	@Inject
	private QuartzJobSchedulerService quartzJobSchedulerService;
	

	public void executeInternal(String compositeJobAndTriggerKey, String triggerName,
			List<JOBSchedulerDetail> jobSchedulerDetails, JobKey jobKey, TriggerKey triggerKey) {

	}

	@Override
	public void executeInternal(String compositeJobAndTriggerKey, JobExecutionContext context) {
		log.info("Job Started Running: {}", compositeJobAndTriggerKey);
		markJobAsRunning(compositeJobAndTriggerKey, context.getTrigger());
		
		executeChainedInternal(compositeJobAndTriggerKey, context);

    	markJobAsComplete(compositeJobAndTriggerKey, context.getTrigger());
	}
	
	protected void executeChainedInternal(String compositeJobAndTriggerKey, String triggerName,
			JOBSchedulerDetail jobSchedulerDetail, TriggerKey triggerKey) {
		
	}
	
	public void executeChainedInternal(String compositeJobAndTriggerKey, JobExecutionContext context) {
		
		boolean fault = Boolean.FALSE;
		int indexCounter = 0;
		Trigger trigger = context.getTrigger();
		String jobName = context.getJobDetail().getKey().getName();
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) context.getMergedJobDataMap().get("jobSchedulerDetail");
		Pair<Boolean, JOBSchedulerExecutionLog> maintainJOBSchedulerExecutionLogKeyValuePair = null;
		String jobMetaInformation = "Data to be set as per requirement";
        try {
        	
        	SchedulerMappedProcessExecutionStatusVO schedulerMappedProcessExecutionStatusVO = 
        			buildAndInitializeSchedulerExecutionMonitorState(jobSchedulerDetail.getMethodToExecute(),"APIFLOWJOB-PROCESS", 1);
			
        	SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorTempVO = 
        			getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, trigger);
			//theMaintainJOBSchedulerExecutionLogKeyValuePair = createExecutionLog(theJOBSchedulerExecutionMappingDetail.getNeoProcessGroupDetail().maintainExecutionLog(), jobSchedulerDetail.getTriggerName(), jobMetaInformation);
        	maintainJOBSchedulerExecutionLogKeyValuePair = createExecutionLog(true, jobSchedulerDetail.getTriggerName(), jobMetaInformation);
        	
        	schedulerJobExecutionMonitorTempVO.getMappedProcessExecutionStatusList().add(schedulerMappedProcessExecutionStatusVO);
			schedulerJobExecutionMonitorTempVO.setRecentExecutionSequence(-1);
			schedulerJobExecutionMonitorTempVO.setJobExecutionLogId(maintainJOBSchedulerExecutionLogKeyValuePair.getLeft() ?  maintainJOBSchedulerExecutionLogKeyValuePair.getRight().getId() : null);

        	/**Deligate Part Start*/
        	/*Class<?> jobClass = Class.forName(jobSchedulerDetail.getClassName());
            Object beanObject = beanAccessHelper.getBean(jobSchedulerDetail.getBeanName(), jobClass);
            Method method = ReflectionUtils.findMethod(beanObject.getClass(), jobSchedulerDetail.getMethodToExecute(), JobExecutionContext.class);
            if (method == null) {
                throw new NoSuchMethodException("Method '" + jobSchedulerDetail.getMethodToExecute() + "' not found on bean '" + jobSchedulerDetail.getBeanName() + "'");
            }
            ReflectionUtils.invokeMethod(method, beanObject, context);
            /**Deligate Part End*/
            
            updateJobExectutionLog(maintainJOBSchedulerExecutionLogKeyValuePair.getLeft(), maintainJOBSchedulerExecutionLogKeyValuePair.getRight());
            schedulerJobExecutionMonitorTempVO.getMappedProcessExecutionStatusList().get(indexCounter).markCompletionSuccess();
        } catch (Exception ex) {
			fault = Boolean.TRUE;
            log.error("Failed to execute job '" + jobName + "'", ex);
        } finally {
			if (fault) {
				markJobAsFailed(maintainJOBSchedulerExecutionLogKeyValuePair, compositeJobAndTriggerKey, indexCounter, trigger);
			}
        }

	}

	protected boolean canTerminateExecutionAndSanitize(Boolean fault, Boolean terminateChainedExecutionOnFault) {
		return fault && terminateChainedExecutionOnFault;
	}

	private void markJobAsRunning(String compositeJobAndTriggerKey, Trigger trigger) {
		SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);
		if (com.pk.development.util.ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
			schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder()
			.setJobGroupName(trigger.getJobKey().getGroup()).setJobName(trigger.getJobKey().getName())
			.setTriggerGroupName(trigger.getKey().getGroup()).setTriggerName(trigger.getKey().getName())
			.setPreviousExecutionTime(trigger.getPreviousFireTime())
			.setNextExecutionTime(trigger.getNextFireTime())
			.setProcessStartTime(new Date())
			.setCurrentStatus(SchedulerStatusEnum.RUNNING.getEnumValue())
			/*.setJobState(((JOBSchedulerDetail) this.jobSchedulerService.getJOBSchedulerDetailByJOBGroupAndName(
					theTrigger.getJobKey().getGroup(), theTrigger.getJobKey().getName()).get(0)).isActiveFlag()
							? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
							: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())*/
			//.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
			//.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())
			.setMappedProcessExecutionStatusList(new LinkedList<>())
			.setThreadName(Thread.currentThread().getName())
			//.setModule(ProductInformationLoader.getProductCode())
			.setModule("APIFlow").build();
			log.info("Running Job: {}", trigger.getKey());
			schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
		} else {
			schedulerJobExecutionMonitorVO.setThreadName(Thread.currentThread().getName());
			schedulerJobExecutionMonitorVO.getMappedProcessExecutionStatusList().clear();//Added
			jobSchedulerHelper.updateDataForTriggerExecution(schedulerJobExecutionMonitorVO, SchedulerStatusEnum.RUNNING.getEnumValue(),
					trigger.getPreviousFireTime(), trigger.getNextFireTime(), schedulerJobExecutionMonitorVO.getJobState());
		}		
		log.info("Job Marked running: {}", schedulerJobExecutionMonitorVO);
	}
	
	private void markJobAsComplete(String compositeJobAndTriggerKey, Trigger trigger) {
		SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);
		if (ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
			schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder()
			.setJobGroupName(trigger.getJobKey().getGroup())
			.setJobName(trigger.getJobKey().getName())
			.setTriggerGroupName(trigger.getKey().getGroup())
			.setTriggerName(trigger.getKey().getName())
			.setPreviousExecutionTime(trigger.getPreviousFireTime())
			.setNextExecutionTime(trigger.getNextFireTime())
			.setProcessStartTime(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
					.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getProcessStartTime())
			.setCurrentStatus(SchedulerStatusEnum.COMPLETED.getEnumValue())
			/*.setJobState(((JOBSchedulerDetail) this.jobSchedulerService
					.getJOBSchedulerDetailByJOBGroupAndName(
							this.getTrigger(triggerKey).getJobKey().getGroup(),
							this.getTrigger(triggerKey).getJobKey().getName())
					.get(0)).isActiveFlag()
							? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
							: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())*/
			//.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
			//.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())
			.setMappedProcessExecutionStatusList(schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap()
					.getOrDefault(compositeJobAndTriggerKey, new SchedulerJobExecutionMonitorVO()).getMappedProcessExecutionStatusList())
			.setThreadName(Thread.currentThread().getName())
			//.setModule(ProductInformationLoader.getProductCode())
			.setModule("APIFlow").build();;
			log.info("Job completed: {}", trigger.getKey());
			schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
		} else {
			schedulerJobExecutionMonitorVO.setThreadName(Thread.currentThread().getName());
			jobSchedulerHelper.updateDataForTriggerExecution(schedulerJobExecutionMonitorVO, SchedulerStatusEnum.COMPLETED.getEnumValue(),
					trigger.getPreviousFireTime(), trigger.getNextFireTime(), schedulerJobExecutionMonitorVO.getJobState());
		}	
	}
	
	private void markJobAsFailed(Pair<Boolean, JOBSchedulerExecutionLog> maintainJOBSchedulerExecutionLogKeyValuePair, String compositeJobAndTriggerKey, int indexCounter, Trigger trigger) {
		if (ValidatorUtils.notNull(maintainJOBSchedulerExecutionLogKeyValuePair)) {
			updateJobExectutionLog(maintainJOBSchedulerExecutionLogKeyValuePair.getLeft(), maintainJOBSchedulerExecutionLogKeyValuePair.getRight());
		}
		SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, trigger);
		schedulerJobExecutionMonitorVO.getMappedProcessExecutionStatusList().get(indexCounter).markCompletionWithFault();
		schedulerJobExecutionMonitorVO.setJobExecutionLogId((Long) null);
		//schedulerExecutionStateData.getSchedulerJobExecutionMonitorVOConcurrentMap().put(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
		//this.neoSchedulerCacheService.aware(compositeJobAndTriggerKey, schedulerJobExecutionMonitorTempVO);

		log.info("Marked job as faild: {}", schedulerJobExecutionMonitorVO);
	}

	
	private SchedulerJobExecutionMonitorVO getSchedulerJobExecutionMonitorVO(String compositeJobAndTriggerKey, Trigger trigger) {
		SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO = schedulerExecutionStateData.getSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey);
		
		if (ValidatorUtils.isNull(schedulerJobExecutionMonitorVO)) {
			schedulerJobExecutionMonitorVO = new SchedulerJobExecutionMonitorVOBuilder()
					.setJobGroupName(trigger.getJobKey().getGroup()).setJobName(trigger.getJobKey().getName())
					.setTriggerGroupName(trigger.getKey().getGroup()).setTriggerName(trigger.getKey().getName())
					.setPreviousExecutionTime(trigger.getPreviousFireTime())
					.setNextExecutionTime(trigger.getNextFireTime())
					//.setProcessStartTime(localSchedulerJobExecutionMonitorVO.getProcessStartTime())
					.setCurrentStatus(SchedulerStatusEnum.RUNNING.getEnumValue())
					/*.setJobState(((JOBSchedulerDetail) this.jobSchedulerService.getJOBSchedulerDetailByJOBGroupAndName(
							theTrigger.getJobKey().getGroup(), theTrigger.getJobKey().getName()).get(0)).isActiveFlag()
									? LMSSchedulerJobStateEnum.ACTIVE.getEnumValue()
									: LMSSchedulerJobStateEnum.INACTIVE.getEnumValue())
					.setTenantId(this.neutrinoExecutionContextHolder.getTenantId())
					.setNodeId(this.clusterOrchestrationService.getThisNodeInfo().getNodeName())*/
					//.setMappedProcessExecutionStatusList(localSchedulerJobExecutionMonitorVO.getMappedProcessExecutionStatusList())
					.setThreadName(Thread.currentThread().getName())
					//.setRecentExecutionSequence(localSchedulerJobExecutionMonitorVO.getRecentExecutionSequence())
					//.setJobExecutionLogId(localSchedulerJobExecutionMonitorVO.getJobExecutionLogId())
					//.setModule(ProductInformationLoader.getProductCode())
					.setModule("APIFlow").build();
			schedulerExecutionStateData.addSchedulerJobExecutionMonitorVO(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
		}

		return schedulerJobExecutionMonitorVO;
	}
	
	protected SchedulerMappedProcessExecutionStatusVO buildAndInitializeSchedulerExecutionMonitorState(String methodToExecute, String processDisplayName, Integer executionSequence) {
		SchedulerMappedProcessExecutionStatusVO theSchedulerMappedProcessExecutionStatusVO = new SchedulerMappedProcessExecutionStatusVO();
		theSchedulerMappedProcessExecutionStatusVO.intializeProcessStartTime();
		theSchedulerMappedProcessExecutionStatusVO.setCurrentStatus(SchedulerStatusEnum.RUNNING.getEnumValue());
		theSchedulerMappedProcessExecutionStatusVO.setMethodToExecute(methodToExecute);
		theSchedulerMappedProcessExecutionStatusVO.setProcessDisplayName(processDisplayName);
		theSchedulerMappedProcessExecutionStatusVO.setExecutionSequence(executionSequence);
		return theSchedulerMappedProcessExecutionStatusVO;
	}
	
	protected Pair<Boolean, JOBSchedulerExecutionLog> createExecutionLog(Boolean maintainExecutionLog, String triggerName, String jobMetaInformation) {
		JOBSchedulerExecutionLog jobSchedulerExecutionLog = null;
		if (maintainExecutionLog) {
			jobSchedulerExecutionLog = jobSchedulerHelper.prepareJobExectutionLog(triggerName, jobMetaInformation);
			jobSchedulerExecutionLog = quartzJobSchedulerService.createJobExectutionLog(jobSchedulerExecutionLog);
		}

		return new ImmutablePair<Boolean, JOBSchedulerExecutionLog>(maintainExecutionLog, jobSchedulerExecutionLog);
	}

	protected void updateJobExectutionLog(Boolean maintainExecutionLog, JOBSchedulerExecutionLog theJOBSchedulerExecutionLog) {
		if (maintainExecutionLog && ValidatorUtils.notNull(theJOBSchedulerExecutionLog)) {
			quartzJobSchedulerService.updateJobExectutionLog(theJOBSchedulerExecutionLog.getId());
		}

	}
	
}