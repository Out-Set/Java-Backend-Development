package com.pk.quartz.scheduler.domainobject;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.BaseEntity;

@Slf4j
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_PROCESS_GRP_HDR")
@NamedQueries({
		@NamedQuery(name = "getAllProcessGroupHeader", query = "select DISTINCT(schedulerProcessGroupHeader) from SchedulerProcessGroupHeader schedulerProcessGroupHeader  WHERE schedulerProcessGroupHeader.tenantId =:tenantId and schedulerProcessGroupHeader.module in (:module) order by schedulerProcessGroupHeader.id ")})
public class SchedulerProcessGroupHeader extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	@Column(name = "PROCESS_GROUP_DISP_NAME", length = 255)
	private String processGroupDisplayName;
	@Column(name = "PROCESS_GROUP_DESC", length = 255)
	private String processGroupDescription;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESS_GROUP_ID", referencedColumnName = "ID")
	private Set<SchedulerProcessGroupDetail> schedulerProcessDetails;
	@Column(name = "TRANSACTION_EVENT", length = 60)
	private String transactionEvent;
	@Column(name = "MODULE", length = 50)
	private String module;
	@Column(name = "EXEC_CONTROL_STATE", columnDefinition = "Numeric(1,0)")
	private Integer executionControlState;

	public String getTransactionEvent() {
		return this.transactionEvent;
	}

	public void setTransactionEvent(String transactionEvent) {
		this.transactionEvent = transactionEvent;
	}

	public String getProcessGroupDisplayName() {
		return this.processGroupDisplayName;
	}

	public void setProcessGroupDisplayName(String processGroupDisplayName) {
		this.processGroupDisplayName = processGroupDisplayName;
	}

	public String getProcessGroupDescription() {
		return this.processGroupDescription;
	}

	public void setProcessGroupDescription(String processGroupDescription) {
		this.processGroupDescription = processGroupDescription;
	}

	public Set<SchedulerProcessGroupDetail> getSchedulerProcessDetails() {
		return this.schedulerProcessDetails;
	}

	public void setSchedulerProcessDetails(Set<SchedulerProcessGroupDetail> schedulerProcessDetails) {
		this.schedulerProcessDetails = schedulerProcessDetails;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

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

	public Integer getExecutionControlState() {
		return this.executionControlState;
	}

	public void setExecutionControlState(Integer executionControlState) {
		this.executionControlState = executionControlState;
	}

	public boolean canKill() {
		return this.getExecutionControlState() == 1;
	}
}
