package com.savan.quartz.scheduler.domainobject;

import java.util.List;

import com.savan.quartz.scheduler.clustercentroid.ConcurrentProcessingProfileType;
import com.savan.quartz.scheduler.clustercentroid.ScaleUpGridProcessType;
import com.savan.quartz.scheduler.constants.ChainedExecutionFaultTreatmentEnum;
import com.savan.quartz.entity.BaseEntity;
import com.savan.quartz.utils.ValidatorUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_PROCESS_GRP_DTL", indexes = {
		@Index(name = "SCHED_PROCESS_GRP_DTL_IDX1", columnList = "PROCESS_GROUP_ID")
})
public class SchedulerProcessGroupDetail extends BaseEntity implements Cloneable {

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
	@Column(name = "CHAINED_EXEC_FAULT_TREATMENT", columnDefinition = "Numeric(1,0)")
	private Integer chainedJobExecutionFaultTreatmentStrategy;
	@Column(name = "MULTI_THREADED", columnDefinition = "Numeric(1,0)")
	private Integer multiThreaded;

	@Column(name = "BASE_PROFILE_TYPE", length = 60)
	private Long concurrentProcessingProfileTypeId;
	@ManyToOne
	@JoinColumn(name = "BASE_PROFILE_TYPE", referencedColumnName = "id", insertable = false, updatable = false)
	private ConcurrentProcessingProfileType concurrentProcessingProfileType;

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

    public Boolean maintainExecutionLog() {
		return 1 == this.getMaintainExecutionLog();
	}

	public Boolean isRunInParallel() {
		return this.runInParallel;
	}

    public Integer getStreamBased() {
		return ValidatorUtils.isNull(this.streamBased) ? 0 : this.streamBased;
	}

    public Boolean streamBased() {
		return 1 == this.getStreamBased();
	}

	public Boolean concurrentEngineBased() {
		return 2 == this.getStreamBased();
	}

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

    /*
	protected void populate(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupDetail schedulerProcessDetail = (SchedulerProcessGroupDetail) baseEntity;
		super.populate(schedulerProcessDetail, cloneOptions);
	}

	protected void populateFrom(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupDetail schedulerProcessDetail = (SchedulerProcessGroupDetail) baseEntity;
		super.populateFrom(schedulerProcessDetail, cloneOptions);
		this.setProcessDisplayName(schedulerProcessDetail.getProcessDisplayName());
		this.setMaintainExecutionLog(schedulerProcessDetail.getMaintainExecutionLog());
	}
	*/
}
