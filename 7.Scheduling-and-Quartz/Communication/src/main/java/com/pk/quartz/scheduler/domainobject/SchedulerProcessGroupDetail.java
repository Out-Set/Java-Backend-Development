package com.pk.quartz.scheduler.domainobject;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.clustercentroid.domainobject.ConcurentProcessingProfileType;
import com.pk.clustercentroid.domainobject.ScaleUpGridProcessType;
import com.pk.development.util.ValidatorUtils;
import com.pk.integration.entity.BaseEntity;
import com.pk.quartz.scheduler.constants.ChainedExecutionFaultTreatmentEnum;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_PROCESS_GRP_DTL", indexes = {
		@Index(name = "SCHED_PROCESS_GRP_DTL_IDX1", columnList = "PROCESS_GROUP_ID")})
@NamedQueries({
		@NamedQuery(name = "getAllProcessGroupDetail", query = "select (schedulerProcessGroupDetail) from SchedulerProcessGroupDetail schedulerProcessGroupDetail  WHERE schedulerProcessGroupDetail.tenantId =:tenantId order by schedulerProcessGroupDetail.id"),
		@NamedQuery(name = "getMultiThreadedProfileBasedOnProcessId", query = "select schedulerProcessGroupDetail.concurentProcessingProfileType from schedulerProcessGroupDetail schedulerProcessGroupDetail WHERE schedulerProcessGroupDetail.scaleUpGridProcessTypeId =:scaleUpGridProcessTypeId and schedulerProcessGroupDetail.tenantId =:tenantId")})
public class SchedulerProcessGroupDetail extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	private static final int ENABLED = 1;
	private static final int CONCURRENT_ENGINE_ENABLED = 2;
	@Column(name = "PROCESS_DISP_NAME", length = 255)
	private String processDisplayName;
	@Column(name = "PROCESS_DESC", length = 255)
	private String processDescription;
	@Column(name = "MAX_EXECUTION_FREQUENCY", columnDefinition = "Numeric(1,0)")
	private Short maximumExecutionFrequency;
	@Column(name = "MAINTAIN_EXECUTION_LOG", columnDefinition = "Numeric(1,0)")
	private Integer maintainExecutionLog = 0;
	@Column(name = "STREAM_BASED", columnDefinition = "Numeric(1,0)")
	private Integer streamBased = 0;
	@Column(name = "RUN_IN_PARALLEL")
	private Boolean runInParallel = false;
	@OneToOne(mappedBy = "schedulerProcessGroupDetail", cascade = {
			CascadeType.ALL}, fetch = FetchType.LAZY, optional = false)
	private SchedulerStreamGroupDetail schedulerStreamGroupDetail;
	@Column(name = "CHAINED_EXEC_FAULT_TREATMENT", columnDefinition = "Numeric(1,0)")
	private Integer chainedJobExecutionFaultTreatmentStrategy;
	@Column(name = "MULTI_THREADED", columnDefinition = "Numeric(1,0)")
	private Integer multiThreaded;
	@Column(name = "BASE_PROFILE_TYPE", length = 60)
	private Long concurentProcessingProfileTypeId;
	@ManyToOne
	@JoinColumn(name = "BASE_PROFILE_TYPE", referencedColumnName = "id", insertable = false, updatable = false)
	private ConcurentProcessingProfileType concurentProcessingProfileType;
	@Column(name = "PROCESS_TYPE_ID", length = 60)
	private Long scaleUpGridProcessTypeId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESS_TYPE_ID", referencedColumnName = "id", insertable = false, updatable = false)
	private ScaleUpGridProcessType scaleUpGridProcessType;
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROCESS_DTL_ID", referencedColumnName = "ID")
	private List<SchedulerProcessDefinitionDetail> schedulerProcessDefinitionDetails;
	@Column(name = "TRANSACTION_EVENT", length = 60)
	private String transactionEvent;

	public SchedulerProcessGroupDetail() {
		this.chainedJobExecutionFaultTreatmentStrategy = ChainedExecutionFaultTreatmentEnum.TERMINATE_CHAIN_ON_FAULT
				.getEnumValue();
		this.multiThreaded = 0;
	}

	public SchedulerStreamGroupDetail getSchedulerStreamGroupDetail() {
		return this.schedulerStreamGroupDetail;
	}

	public void setSchedulerStreamGroupDetail(SchedulerStreamGroupDetail schedulerStreamGroupDetail) {
		this.schedulerStreamGroupDetail = schedulerStreamGroupDetail;
	}

	public String getProcessDisplayName() {
		return this.processDisplayName;
	}

	public void setProcessDisplayName(String processDisplayName) {
		this.processDisplayName = processDisplayName;
	}

	public String getProcessDescription() {
		return this.processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public Short getMaximumExecutionFrequency() {
		return this.maximumExecutionFrequency;
	}

	public void setMaximumExecutionFrequency(Short maximumExecutionFrequency) {
		this.maximumExecutionFrequency = maximumExecutionFrequency;
	}

	public Integer getMaintainExecutionLog() {
		return this.maintainExecutionLog;
	}

	public void setMaintainExecutionLog(Integer maintainExecutionLog) {
		this.maintainExecutionLog = maintainExecutionLog;
	}

	public Boolean maintainExecutionLog() {
		return 1 == this.getMaintainExecutionLog();
	}

	public Boolean isRunInParallel() {
		return this.runInParallel;
	}

	public void setRunInParallel(Boolean runInParallel) {
		this.runInParallel = runInParallel;
	}

	public Integer getStreamBased() {
		return ValidatorUtils.isNull(this.streamBased) ? 0 : this.streamBased;
	}

	public void setStreamBased(Integer streamBased) {
		this.streamBased = streamBased;
	}

	public Boolean streamBased() {
		return 1 == this.getStreamBased();
	}

	public Boolean concurrentEngineBased() {
		return 2 == this.getStreamBased();
	}

	public Boolean getRunInParallel() {
		return this.runInParallel;
	}

	public Integer getChainedJobExecutionFaultTreatmentStrategy() {
		return this.chainedJobExecutionFaultTreatmentStrategy;
	}

	public void setChainedJobExecutionFaultTreatmentStrategy(Integer chainedJobExecutionFaultTreatmentStrategy) {
		this.chainedJobExecutionFaultTreatmentStrategy = chainedJobExecutionFaultTreatmentStrategy;
	}

	public Integer getMultiThreaded() {
		return this.multiThreaded;
	}

	public void setMultiThreaded(Integer multiThreaded) {
		this.multiThreaded = multiThreaded;
	}

	public Long getConcurentProcessingProfileTypeId() {
		return this.concurentProcessingProfileTypeId;
	}

	public void setConcurentProcessingProfileTypeId(Long concurentProcessingProfileTypeId) {
		this.concurentProcessingProfileTypeId = concurentProcessingProfileTypeId;
	}

	public ConcurentProcessingProfileType getConcurentProcessingProfileType() {
		return this.concurentProcessingProfileType;
	}

	public void setConcurentProcessingProfileType(ConcurentProcessingProfileType concurentProcessingProfileType) {
		this.concurentProcessingProfileType = concurentProcessingProfileType;
	}

	public Long getScaleUpGridProcessTypeId() {
		return this.scaleUpGridProcessTypeId;
	}

	public void setScaleUpGridProcessTypeId(Long scaleUpGridProcessTypeId) {
		this.scaleUpGridProcessTypeId = scaleUpGridProcessTypeId;
	}

	public ScaleUpGridProcessType getScaleUpGridProcessType() {
		return this.scaleUpGridProcessType;
	}

	public void setScaleUpGridProcessType(ScaleUpGridProcessType scaleUpGridProcessType) {
		this.scaleUpGridProcessType = scaleUpGridProcessType;
	}

	public List<SchedulerProcessDefinitionDetail> getSchedulerProcessDefinitionDetails() {
		return this.schedulerProcessDefinitionDetails;
	}

	public void setSchedulerProcessDefinitionDetails(List<SchedulerProcessDefinitionDetail> schedulerProcessDefinitionDetails) {
		this.schedulerProcessDefinitionDetails = schedulerProcessDefinitionDetails;
	}

	public String getTransactionEvent() {
		return this.transactionEvent;
	}

	public void setTransactionEvent(String transactionEvent) {
		this.transactionEvent = transactionEvent;
	}

	/*protected void populate(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupDetail schedulerProcessDetail = (SchedulerProcessGroupDetail) baseEntity;
		super.populate(schedulerProcessDetail, cloneOptions);
	}

	protected void populateFrom(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupDetail schedulerProcessDetail = (SchedulerProcessGroupDetail) baseEntity;
		super.populateFrom(schedulerProcessDetail, cloneOptions);
		this.setProcessDisplayName(schedulerProcessDetail.getProcessDisplayName());
		this.setMaintainExecutionLog(schedulerProcessDetail.getMaintainExecutionLog());
	}*/

	public SchedulerProcessGroupDetail clone() {
		SchedulerProcessGroupDetail cloneSchedulerProcessGroupDetail = null;

		try {
			cloneSchedulerProcessGroupDetail = (SchedulerProcessGroupDetail) super.clone();
		} catch (CloneNotSupportedException var3) {
			log.error(var3.getMessage(), var3);
		}

		return cloneSchedulerProcessGroupDetail;
	}

	public String toString() {
		return "SchedulerProcessGroupDetail [processDisplayName=" + this.processDisplayName + ", processDescription="
				+ this.processDescription + "]";
	}
}