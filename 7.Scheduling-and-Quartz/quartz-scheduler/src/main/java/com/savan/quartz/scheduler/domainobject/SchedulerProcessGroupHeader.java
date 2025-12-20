package com.savan.quartz.scheduler.domainobject;

import java.util.Set;

import com.savan.quartz.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Setter
@Getter
@Slf4j
@Entity
@ToString
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_PROCESS_GRP_HDR")
public class SchedulerProcessGroupHeader extends BaseEntity implements Cloneable {

	@Column(name = "PROCESS_GROUP_DISP_NAME")
	private String processGroupDisplayName;

	@Column(name = "PROCESS_GROUP_DESC")
	private String processGroupDescription;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESS_GROUP_ID", referencedColumnName = "ID")
	private Set<SchedulerProcessGroupDetail> schedulerProcessGroupDetails;

	@Column(name = "TRANSACTION_EVENT", length = 60)
	private String transactionEvent;

	@Column(name = "MODULE", length = 50)
	private String module;

	@Column(name = "EXEC_CONTROL_STATE", columnDefinition = "Numeric(1,0)")
	private Integer executionControlState;

    /*protected void populate(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupHeader schedulerGroup = (SchedulerProcessGroupHeader) baseEntity;
		super.populate(schedulerGroup, cloneOptions);
	}

	protected void populateFrom(BaseEntity baseEntity, CloneOptions cloneOptions) {
		SchedulerProcessGroupHeader schedulerGroup = (SchedulerProcessGroupHeader) baseEntity;
		super.populateFrom(schedulerGroup, cloneOptions);
		this.setProcessGroupDisplayName(schedulerGroup.getProcessGroupDescription());
		this.setExecutionControlState(schedulerGroup.getExecutionControlState());
	}*/

	public SchedulerProcessGroupHeader clone() {
		SchedulerProcessGroupHeader cloneSchedulerProcessGroupHeader = null;
		try {
			cloneSchedulerProcessGroupHeader = (SchedulerProcessGroupHeader) super.clone();
		} catch (CloneNotSupportedException var3) {
			log.error(var3.getMessage(), var3);
		}
		return cloneSchedulerProcessGroupHeader;
	}

    public boolean canKill() {
		return this.getExecutionControlState() == 1;
	}
}
