package com.savan.quartz.scheduler.domainobject;

import java.io.Serializable;

import com.savan.quartz.entity.BaseEntity;
import com.savan.quartz.scheduler.clustercentroid.ConcurrentProcessingStageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_PROCESS_DEF_DTL", indexes = {
		@Index(name = "SCHED_PROCESS_DEF_DTL_IDX1", columnList = "PROCESS_DTL_ID")
})
public class SchedulerProcessDefinitionDetail extends BaseEntity implements Serializable {

	@Column(name = "BEAN_ID")
	private String beanId;
	@Column(name = "CLASS_NAME")
	private String className;
	@Column(name = "METHOD_TO_EXECUTE")
	private String methodToExecute;

    @Column(name = "STAGE_TYPE", length = 60)
    private Long concurrentProcessingStageTypeId;
    @ManyToOne
    @JoinColumn(name = "STAGE_TYPE", referencedColumnName = "id", insertable = false, updatable = false)
    private ConcurrentProcessingStageType concurrentProcessingStageType;

}