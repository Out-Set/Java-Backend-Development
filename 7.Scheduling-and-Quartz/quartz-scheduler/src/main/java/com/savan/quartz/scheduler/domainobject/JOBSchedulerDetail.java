package com.savan.quartz.scheduler.domainobject;

import java.io.Serial;
import java.util.Set;

import com.savan.quartz.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Entity
@ToString
@DynamicInsert
@DynamicUpdate
@Table(name = "COM_SCHEDULER_JOB_DTL", indexes = {@Index(name = "COM_SCHEDULER_JOB_DTL_IDX1", columnList = "JOB_GROUP")})
public class JOBSchedulerDetail extends BaseEntity implements Cloneable {
    
	@Serial
    private static final long serialVersionUID = 1L;
    
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "JOB_SCHED_DTL_ID", referencedColumnName = "ID")
	@OrderBy("executionSequence")
	private Set<JOBSchedulerExecutionMappingDetail> jobSchedulerExecutionMappingDetails;
    
	private boolean activeFlag = true;

    @Column(name = "JOB_NAME")
    private String jobName;
    
	@Column(name = "JOB_GROUP")
	private Long processGroupHeaderId;
	@ManyToOne
	@JoinColumn(name = "JOB_GROUP", referencedColumnName = "ID", updatable = false, insertable = false)
	private SchedulerProcessGroupHeader processGroupType;

	@Column(name = "TRIGGER_NAME", length = 283)
	private String triggerName;
    
	@Column(name = "TRIGGER_GROUP", length = 283)
	private String triggerGroupName;
    
	@Column(name = "CRON_EXPRESSION")
	private String cronExpression;

    @Column(name = "CALENDAR_ID")
    private String calendarId;
    
	@Column(name = "RUN_ON_HOLIDAY", columnDefinition = "Numeric(1,0)")
	private Integer runOnHoliday = 0;
    
	@Column(name = "LEGACY_CRON_BUILDER_FLAG", columnDefinition = "Numeric(1,0)")
	private Integer cronBuilderSelector = 0;
    
	@Column(name = "MODULE")
	private String sourceProduct;
    
	@Column(name = "ENV")
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
    private String beanName;
    
	@Transient
	private String beanId;
    
	@Transient
	private String jobId;

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

	protected void populate(BaseEntity baseEntity) {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) baseEntity;
		//super.populate(jobSchedulerDetail);
		//jobSchedulerDetail.setTenantId(this.getTenantId());
		jobSchedulerDetail.setTriggerName(this.getTriggerName());
		jobSchedulerDetail.setTriggerGroupName(this.getTriggerGroupName());
		jobSchedulerDetail.setCronExpression(this.getCronExpression());
		jobSchedulerDetail.setProcessGroupHeaderId((this.getProcessGroupHeaderId()));
		jobSchedulerDetail.setRunOnHoliday(this.getRunOnHoliday());
		jobSchedulerDetail.setCronBuilderSelector(this.getCronBuilderSelector());
		//jobSchedulerDetail.setNeoProcessGroupType(this.getNeoProcessGroupType());
		jobSchedulerDetail.setActiveFlag(this.isActiveFlag());
		jobSchedulerDetail.setJobName(this.getJobName());
		jobSchedulerDetail.setSourceProduct(this.getSourceProduct());
        /*
		if (ValidatorUtils.hasElements(this.jobSchedulerExecutionMappingDetails)) {
			Set<JOBSchedulerExecutionMappingDetail> cloneDetails = new HashSet();
			Iterator var5 = this.jobSchedulerExecutionMappingDetails.iterator();
			while (var5.hasNext()) {
				JOBSchedulerExecutionMappingDetail theJOBSchedulerExecutionMappingDetail = (JOBSchedulerExecutionMappingDetail) var5
						.next();
				cloneDetails.add((JOBSchedulerExecutionMappingDetail) theJOBSchedulerExecutionMappingDetail
						.cloneYourself(cloneOptions));
			}
			jobSchedulerDetail.setJobSchedulerExecutionMappingDetails(cloneDetails);
		}
        */
	}

	protected void populateFrom(BaseEntity baseEntity) {
		JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) baseEntity;
		//super.populateFrom(jobSchedulerDetail, cloneOptions);
		//this.setTenantId(jobSchedulerDetail.getTenantId());
		this.setTriggerName(jobSchedulerDetail.getTriggerName());
		this.setTriggerGroupName(jobSchedulerDetail.getTriggerGroupName());
		this.setCronExpression(jobSchedulerDetail.getCronExpression());
		this.setProcessGroupHeaderId(jobSchedulerDetail.getProcessGroupHeaderId());
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

	public JOBSchedulerDetail clone() {
		JOBSchedulerDetail cloneJOBSchedulerDetail = null;
		try {
			cloneJOBSchedulerDetail = (JOBSchedulerDetail) super.clone();
		} catch (CloneNotSupportedException ex) {
			log.error(ex.getMessage(), ex);
		}
		return cloneJOBSchedulerDetail;
	}
}