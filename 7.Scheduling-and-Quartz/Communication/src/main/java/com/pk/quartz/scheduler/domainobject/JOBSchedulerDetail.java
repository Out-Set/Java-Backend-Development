package com.pk.quartz.scheduler.domainobject;

import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "COM_SCHEDULER_JOB_DTL", indexes = {@Index(name = "COM_SCHEDULER_JOB_DTL_IDX1", columnList = "JOB_GROUP")})
@NamedQueries({
		@NamedQuery(name = "getRegisteredJobs", query = "select jobSchedulerDetail from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId And jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus And jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.sourceProduct =:sourceProduct order by jobSchedulerDetail.id "),
		@NamedQuery(name = "getJOBSchedulerDetailByTriggergGroupAndName", query = "select distinct new com.nucleus.finnone.pro.scheduler.domainobject.JOBSchedulerDetailVO(jobSchedulerDetail.jobName, jobSchedulerDetail.neoProcessGroupId, jobSchedulerDetail.triggerName, jobSchedulerDetail.triggerGroupName) from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId And jobSchedulerDetail.triggerGroupName = :triggerGroupName And jobSchedulerDetail.triggerName =:triggerName And jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getJOBSchedulerDetailVOByJobGroupAndName", query = "select distinct new com.nucleus.finnone.pro.scheduler.domainobject.JOBSchedulerDetailVO(jobSchedulerDetail.jobName, jobSchedulerDetail.neoProcessGroupId, jobSchedulerDetail.triggerName, jobSchedulerDetail.triggerGroupName,jobSchedulerDetail.activeFlag) from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId And jobSchedulerDetail.neoProcessGroupId = :jobGroupName And jobSchedulerDetail.jobName = :jobName And jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getJOBSchedulerDetailByJobGroupAndName", query = "select jobSchedulerDetail from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId And jobSchedulerDetail.neoProcessGroupId = :jobGroupName And jobSchedulerDetail.jobName = :jobName And jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getScheduledJobsByTriggerName", query = "select jobSchedulerDetail from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.triggerName =:triggerName and jobSchedulerDetail.tenantId =:tenantId  and jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.activeFlag=:activeFlag And jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getScheduledJobByJobGroupId", query = "select distinct(jobSchedulerDetail) from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId and jobSchedulerDetail.neoProcessGroupId =:neoProcessGroupId  and jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getActiveScheduledJobByJobName", query = "select jobSchedulerDetail from JOBSchedulerDetail jobSchedulerDetail WHERE jobSchedulerDetail.tenantId =:tenantId and jobSchedulerDetail.jobName =:jobName and jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct"),
		@NamedQuery(name = "getAllMappedProcessIdAccrossAllProcessGroups", query = "select distinct jobSchedulerExecutionMappings.neoProcessGroupDetailId from JOBSchedulerDetail jobSchedulerDetail inner join jobSchedulerDetail.jobSchedulerExecutionMappingDetails jobSchedulerExecutionMappings WHERE jobSchedulerDetail.tenantId =:tenantId and jobSchedulerDetail.masterLifeCycleData.approvalStatus IN (:approvalStatus) and jobSchedulerDetail.entityLifeCycleData.persistenceStatus= :persistenceStatus and jobSchedulerDetail.sourceProduct =:sourceProduct order by jobSchedulerExecutionMappings.neoProcessGroupDetailId ")})
public class JOBSchedulerDetail extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "JOB_SCHED_DTL_ID", referencedColumnName = "ID")
	@OrderBy("executionSequence")
	private Set<JOBSchedulerExecutionMappingDetail> jobSchedulerExecutionMappingDetails;
	private boolean activeFlag = true;
	@Column(name = "JOB_GROUP")
	private String processGroupId;
	@ManyToOne
	@JoinColumn(name = "JOB_GROUP", referencedColumnName = "ID", updatable = false, insertable = false)
	private SchedulerProcessGroupHeader neoProcessGroupType;
	@Column(name = "TRIGGER_NAME", length = 283)
	private String triggerName;
	@Column(name = "TRIGGER_GROUP", length = 283)
	private String triggerGroupName;
	@Column(name = "CRON_EXPRESSION", length = 255)
	private String cronExpression;
	@Column(name = "JOB_NAME", length = 255)
	private String jobName;
	@Column(name = "RUN_ON_HOLIDAY", columnDefinition = "Numeric(1,0)")
	private Integer runOnHoliday = 0;
	@Column(name = "LEGACY_CRON_BUILDER_FLAG", columnDefinition = "Numeric(1,0)")
	private Integer cronBuilderSelector = 0;
	@Column(name = "MODULE", length = 255)
	private String sourceProduct;
	@Column(name = "ENV", length = 255)
	private String environment;
	@Transient
	private Long jobExecutionLogId;
	@Transient
	private String processSequence;
	@Transient
	private String className;
	@Transient
	private String methodToExecute;
	@Transient
	private String beanId;
	@Transient
	private String jobId;

	public Set<JOBSchedulerExecutionMappingDetail> getJobSchedulerExecutionMappingDetails() {
		return this.jobSchedulerExecutionMappingDetails;
	}

	public void setJobSchedulerExecutionMappingDetails(
			Set<JOBSchedulerExecutionMappingDetail> jobSchedulerExecutionMappingDetails) {
		this.jobSchedulerExecutionMappingDetails = jobSchedulerExecutionMappingDetails;
	}
	
	public boolean isActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getProcessGroupId() {
		return this.processGroupId;
	}

	public void setProcessGroupId(String processGroup) {
		this.processGroupId = processGroup;
	}

	/*public NeoSchedulerProcessGroupHeader getNeoProcessGroupType() {
		return this.neoProcessGroupType;
	}

	public void setNeoProcessGroupType(NeoSchedulerProcessGroupHeader neoProcessGroupType) {
		this.neoProcessGroupType = neoProcessGroupType;
	}*/

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

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getRunOnHoliday() {
		return this.runOnHoliday;
	}

	public void setRunOnHoliday(Integer runOnHoliday) {
		this.runOnHoliday = runOnHoliday;
	}

	public Integer getCronBuilderSelector() {
		return this.cronBuilderSelector;
	}

	public void setCronBuilderSelector(Integer cronBuilderSelector) {
		this.cronBuilderSelector = cronBuilderSelector;
	}

	public Long getJobExecutionLogId() {
		return this.jobExecutionLogId;
	}

	public void setJobExecutionLogId(Long jobExecutionLogId) {
		this.jobExecutionLogId = jobExecutionLogId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodToExecute() {
		return this.methodToExecute;
	}

	public void setMethodToExecute(String methodToExecute) {
		this.methodToExecute = methodToExecute;
	}

	public String getBeanId() {
		return this.beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getProcessSequence() {
		return this.processSequence;
	}

	public void setProcessSequence(String processSequence) {
		this.processSequence = processSequence;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSourceProduct() {
		return this.sourceProduct;
	}

	public void setSourceProduct(String sourceProduct) {
		this.sourceProduct = sourceProduct;
	} 

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	protected void populate(BaseEntity baseEntity) {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) baseEntity;
		//super.populate(jobSchedulerDetail);
		//jobSchedulerDetail.setTenantId(this.getTenantId());
		jobSchedulerDetail.setTriggerName(this.getTriggerName());
		jobSchedulerDetail.setTriggerGroupName(this.getTriggerGroupName());
		jobSchedulerDetail.setCronExpression(this.getCronExpression());
		jobSchedulerDetail.setProcessGroupId(this.getProcessGroupId());
		jobSchedulerDetail.setRunOnHoliday(this.getRunOnHoliday());
		jobSchedulerDetail.setCronBuilderSelector(this.getCronBuilderSelector());
		//jobSchedulerDetail.setNeoProcessGroupType(this.getNeoProcessGroupType());
		jobSchedulerDetail.setActiveFlag(this.isActiveFlag());
		jobSchedulerDetail.setJobName(this.getJobName());
		jobSchedulerDetail.setSourceProduct(this.getSourceProduct());
		/*if (ValidatorUtils.hasElements(this.jobSchedulerExecutionMappingDetails)) {
			Set<JOBSchedulerExecutionMappingDetail> cloneDetails = new HashSet();
			Iterator var5 = this.jobSchedulerExecutionMappingDetails.iterator();

			while (var5.hasNext()) {
				JOBSchedulerExecutionMappingDetail theJOBSchedulerExecutionMappingDetail = (JOBSchedulerExecutionMappingDetail) var5
						.next();
				cloneDetails.add((JOBSchedulerExecutionMappingDetail) theJOBSchedulerExecutionMappingDetail
						.cloneYourself(cloneOptions));
			}

			jobSchedulerDetail.setJobSchedulerExecutionMappingDetails(cloneDetails);
		}*/

	}

	protected void populateFrom(BaseEntity baseEntity) {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) baseEntity;
		//super.populateFrom(jobSchedulerDetail, cloneOptions);
		//this.setTenantId(jobSchedulerDetail.getTenantId());
		this.setTriggerName(jobSchedulerDetail.getTriggerName());
		this.setTriggerGroupName(jobSchedulerDetail.getTriggerGroupName());
		this.setCronExpression(jobSchedulerDetail.getCronExpression());
		this.setProcessGroupId(jobSchedulerDetail.getProcessGroupId());
		this.setRunOnHoliday(jobSchedulerDetail.getRunOnHoliday());
		this.setCronBuilderSelector(jobSchedulerDetail.getCronBuilderSelector());
		//this.setNeoProcessGroupType(jobSchedulerDetail.getNeoProcessGroupType());
		this.setActiveFlag(jobSchedulerDetail.isActiveFlag());
		this.setJobName(jobSchedulerDetail.getJobName());
		this.setSourceProduct(jobSchedulerDetail.getSourceProduct());
		/*if (ValidatorUtils.hasElements(jobSchedulerDetail.getJobSchedulerExecutionMappingDetails())) {
			Set<JOBSchedulerExecutionMappingDetail> cloneDetails = new HashSet();
			Iterator var5 = jobSchedulerDetail.getJobSchedulerExecutionMappingDetails().iterator();

			while (var5.hasNext()) {
				JOBSchedulerExecutionMappingDetail theJOBSchedulerExecutionMappingDetail = (JOBSchedulerExecutionMappingDetail) var5
						.next();
				cloneDetails.add((JOBSchedulerExecutionMappingDetail) theJOBSchedulerExecutionMappingDetail
						.cloneYourself(cloneOptions));
			}

			this.setJobSchedulerExecutionMappingDetails(cloneDetails);
		}*/

	}

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/*public String getDisplayName() {
		return this.getNeoProcessGroupType().getProcessGroupDisplayName();
	}*/

	public JOBSchedulerDetail clone() {
		JOBSchedulerDetail cloneJOBSchedulerDetail = null;

		try {
			cloneJOBSchedulerDetail = (JOBSchedulerDetail) super.clone();
		} catch (CloneNotSupportedException ex) {
			log.error(ex.getMessage(), ex);
		}

		return cloneJOBSchedulerDetail;
	}

	public void doPause() {
		this.setActiveFlag(Boolean.FALSE);
	}

	public void doResume() {
		this.setActiveFlag(Boolean.TRUE);
	}

	public Boolean canPause() {
		return this.isActiveFlag();
	}

	public Boolean canResume() {
		return !this.isActiveFlag();
	}
}