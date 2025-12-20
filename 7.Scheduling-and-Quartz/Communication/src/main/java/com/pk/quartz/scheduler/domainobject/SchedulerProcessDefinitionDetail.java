package com.pk.quartz.scheduler.domainobject;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.BaseEntity;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHD_PROCESS_DEF_DTL", indexes = {
		@Index(name = "SCHD_PROCESS_DEF_DTL_IDX1", columnList = "PROCESS_DTL_ID")})
public class SchedulerProcessDefinitionDetail extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "BEAN_ID", length = 255)
	private String beanId;
	@Column(name = "CLASS_NAME", length = 255)
	private String className;
	@Column(name = "METHOD_TO_EXECUTE", length = 255)
	private String methodToExecute;
	@Column(name = "STAGE_TYPE", length = 60)
	private Long concurentProcessingStageTypeId;
	@ManyToOne
	@JoinColumn(name = "STAGE_TYPE", referencedColumnName = "id", insertable = false, updatable = false)
	private ConcurentProcessingStageType concurentProcessingStageType;

	public String getBeanId() {
		return this.beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
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

	public Long getConcurentProcessingStageTypeId() {
		return this.concurentProcessingStageTypeId;
	}

	public void setConcurentProcessingStageTypeId(Long concurentProcessingStageTypeId) {
		this.concurentProcessingStageTypeId = concurentProcessingStageTypeId;
	}

	public ConcurentProcessingStageType getConcurentProcessingStageType() {
		return this.concurentProcessingStageType;
	}

	public void setConcurentProcessingStageType(ConcurentProcessingStageType concurentProcessingStageType) {
		this.concurentProcessingStageType = concurentProcessingStageType;
	}
}