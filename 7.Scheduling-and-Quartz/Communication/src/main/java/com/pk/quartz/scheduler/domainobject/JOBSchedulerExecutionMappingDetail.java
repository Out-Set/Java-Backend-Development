package com.pk.quartz.scheduler.domainobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.BaseEntity;
import com.pk.quartz.scheduler.constants.ChainedExecutionFaultTreatmentEnum;

@Slf4j
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "COM_SCHEDULER_EXEC_MAP_DTL", indexes = {
		@Index(name = "COM_SCHEDULER_EXEC_MAP_IDX1", columnList = "JOB_SCHED_DTL_ID"),
		@Index(name = "COM_SCHEDULER_EXEC_MAP_IDX2", columnList = "SCHED_PROC_GRP_DTL_ID")})
@NamedQueries({
		@NamedQuery(name = "getAllProcessMappingDetail", query = "select (jobSchedulerExecutionMappingDetail) from JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail WHERE jobSchedulerExecutionMappingDetail.tenantId =:tenantId order by jobSchedulerExecutionMappingDetail.id ")})
public class JOBSchedulerExecutionMappingDetail extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	@Column(name = "EXEC_SEQUENCE", columnDefinition = "Numeric(3,0)")
	private Integer executionSequence;
	@Column(name = "SCHED_PROC_GRP_DTL_ID")
	private Long processGroupDetailId;
	@ManyToOne
	@JoinColumn(name = "SCHED_PROC_GRP_DTL_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private SchedulerProcessGroupDetail processGroupDetail;
	@Transient
	@Column(name = "CHAINED_EXEC_FAULT_TREATMENT")
	private Integer chainedJobExecutionFaultTreatmentStrategy;

	public JOBSchedulerExecutionMappingDetail() {
		this.chainedJobExecutionFaultTreatmentStrategy = ChainedExecutionFaultTreatmentEnum.TERMINATE_CHAIN_ON_FAULT
				.getEnumValue();
	}

	public Integer getExecutionSequence() {
		return this.executionSequence;
	}

	public void setExecutionSequence(Integer executionSequence) {
		this.executionSequence = executionSequence;
	}

	public SchedulerProcessGroupDetail getProcessGroupDetail() {
		return this.processGroupDetail;
	}

	public void setProcessGroupDetail(SchedulerProcessGroupDetail processDetail) {
		this.processGroupDetail = processDetail;
	}

	public Long getProcessGroupDetailId() {
		return this.processGroupDetailId;
	}

	public void setProcessGroupDetailId(Long processDetailId) {
		this.processGroupDetailId = processDetailId;
	}

	public Integer getChainedJobExecutionFaultTreatmentStrategy() {
		return this.chainedJobExecutionFaultTreatmentStrategy;
	}

	public Boolean digestAndMoveOnChainedExecutionFault() {
		return ChainedExecutionFaultTreatmentEnum.DIGEST_AND_MOVE_ON
				.equalsValue(this.chainedJobExecutionFaultTreatmentStrategy);
	}

	public Boolean terminateChainedExecutionFault() {
		return ChainedExecutionFaultTreatmentEnum.TERMINATE_CHAIN_ON_FAULT
				.equalsValue(this.chainedJobExecutionFaultTreatmentStrategy);
	}

	public void setChainedJobExecutionFaultTreatmentStrategy(Integer chainedJobExecutionFaultTreatmentStrategy) {
		this.chainedJobExecutionFaultTreatmentStrategy = chainedJobExecutionFaultTreatmentStrategy;
	}

	/*protected void populate(BaseEntity baseEntity, CloneOptions cloneOptions) {
		JOBSchedulerExecutionMappingDetail jobSchedulerMapping = (JOBSchedulerExecutionMappingDetail) baseEntity;
		super.populate(jobSchedulerMapping, cloneOptions);
		jobSchedulerMapping.setTenantId(this.getTenantId());
		jobSchedulerMapping.setProcessGroupDetailId(this.getProcessGroupDetailId());
		jobSchedulerMapping.setProcessGroupDetail(this.getProcessGroupDetail());
		jobSchedulerMapping.setExecutionSequence(this.getExecutionSequence());
		jobSchedulerMapping
				.setChainedJobExecutionFaultTreatmentStrategy(this.getChainedJobExecutionFaultTreatmentStrategy());
	}

	protected void populateFrom(BaseEntity baseEntity, CloneOptions cloneOptions) {
		JOBSchedulerExecutionMappingDetail jobSchedulerMapping = (JOBSchedulerExecutionMappingDetail) baseEntity;
		super.populateFrom(jobSchedulerMapping, cloneOptions);
		this.setTenantId(this.getTenantId());
		this.setProcessGroupDetailId(this.getProcessGroupDetailId());
		this.setProcessGroupDetail(this.getProcessGroupDetail());
		this.setExecutionSequence(this.getExecutionSequence());
		this.setChainedJobExecutionFaultTreatmentStrategy(this.getChainedJobExecutionFaultTreatmentStrategy());
	}*/

	public JOBSchedulerExecutionMappingDetail clone() {
		JOBSchedulerExecutionMappingDetail cloneJOBSchedulerExecutionMappingDetail = null;

		try {
			cloneJOBSchedulerExecutionMappingDetail = (JOBSchedulerExecutionMappingDetail) super.clone();
		} catch (CloneNotSupportedException var3) {
			log.error(var3.getMessage(), var3);
		}

		return cloneJOBSchedulerExecutionMappingDetail;
	}

	public String toString() {
		return "JOBSchedulerExecutionMappingDetail [JOBSchedulerExecutionMappingDetail.Id " + this.getId()
				+ ", executionSequence=" + this.executionSequence + ", SchedulerProcessGroupDetail.Id="
				+ this.processGroupDetailId + ", ProcessGroupDetail={" + this.processGroupDetail + "}]";
	}
}
