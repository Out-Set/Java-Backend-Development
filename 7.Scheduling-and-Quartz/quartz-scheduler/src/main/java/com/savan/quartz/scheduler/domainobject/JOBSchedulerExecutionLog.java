package com.savan.quartz.scheduler.domainobject;

import java.time.LocalDateTime;

import com.savan.quartz.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "COM_SCHEDULER_JOB_EXEC_LOG")
public class JOBSchedulerExecutionLog extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(name = "TRIGGER_NAME", length = 255)
	private String triggerName;
	@Column(name = "JOB_META_INFORMATION", length = 2000)
	private String jobMetaInformation;
	@Column(name = "EXECUTION_START_TIME", columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime executionStartTime;
	@Column(name = "EXECUTION_END_TIME", columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime executionEndTime;
	@Column(name = "MACHINE_NAME", length = 200)
	private String machineName;
}
