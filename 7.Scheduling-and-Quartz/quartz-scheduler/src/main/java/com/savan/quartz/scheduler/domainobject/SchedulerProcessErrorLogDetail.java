package com.savan.quartz.scheduler.domainobject;

import java.util.Date;

import com.savan.quartz.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Setter
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCHEDULER_ERROR_LOG_DTL")
public class SchedulerProcessErrorLogDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "PROCESS_BUSINESSDATE", columnDefinition = "Date")
	private Date processBusinessDate;

	@Column(name = "SCHEDULER_EVENT", length = 150)
	private String schedulerEvent;

	@Column(name = "SCHED_PROCESSID", columnDefinition = "Numeric(19,0)")
	private Long schedulerProcessId;

	@Column(name = "ERROR_TYPE", columnDefinition = "Numeric(1,0)")
	private Long errorType;

	@Column(name = "ERROR_MESSAGEID", length = 500)
	private String errorMessageId;

	@Column(name = "JOB_META_INFORMATION", length = 2000)
	private String jobMetaInformation;

	@Column(name = "ERROR_MESSAGE_PARAMETERS", length = 2000)
	private String errorMessageParameters;

	@Column(name = "ERROR_DESCRIPTION_EN", length = 4000)
	private String errorDescription;

	@Lob
	@Column(name = "EXCEPTION_STACK_DESCRIPTION")
	private String exceptionStackDescription;

	private SchedulerProcessErrorLogDetail(Builder builder) {
		this.processBusinessDate = builder.processBusinessDate;
		this.schedulerEvent = builder.schedulerEvent;
		this.schedulerProcessId = builder.schedulerProcessId;
		this.errorType = builder.errorType;
		this.errorMessageId = builder.errorMessageId;
		this.errorMessageParameters = builder.errorMessageParameters;
		this.errorDescription = builder.errorDescription;
		this.exceptionStackDescription = builder.exceptionStackDescription;
		this.jobMetaInformation = builder.jobMetaInformation;
	}

	/*
	public int hashCode() {
		int prime = true;
		int result = super.hashCode();
		result = 31 * result + (this.getId() == null ? 0 : this.getId().hashCode());
		return result;
	}
	*/

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equals(obj)) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			SchedulerProcessErrorLogDetail other = (SchedulerProcessErrorLogDetail) obj;
			if (this.getId() == null) {
				if (other.getId() != null) {
					return false;
				}
			} else if (!this.getId().equals(other.getId())) {
				return false;
			}
			return true;
		}
	}

    public static class Builder {
		private Long id;
		private Long tenantId;
		private Date processBusinessDate;
		private String schedulerEvent;
		private Long schedulerProcessId;
		private Long errorType;
		private String errorMessageId;
		private String errorMessageParameters;
		private String errorDescription;
		private String exceptionStackDescription;
		private String jobMetaInformation;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder tenantId(Long tenantId) {
			this.tenantId = tenantId;
			return this;
		}

		public Builder processBusinessDate(Date processBusinessDate) {
			this.processBusinessDate = processBusinessDate;
			return this;
		}

		public Builder schedulerEvent(String schedulerEvent) {
			this.schedulerEvent = schedulerEvent;
			return this;
		}

		public Builder schedulerProcessId(Long schedulerProcessId) {
			this.schedulerProcessId = schedulerProcessId;
			return this;
		}

		public Builder errorType(Long errorType) {
			this.errorType = errorType;
			return this;
		}

		public Builder errorMessageId(String errorMessageId) {
			this.errorMessageId = errorMessageId;
			return this;
		}

		public Builder errorMessageParameters(String errorMessageParameters) {
			this.errorMessageParameters = errorMessageParameters;
			return this;
		}

		public Builder errorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
			return this;
		}

		public Builder jobMetaInformation(String jobMetaInformation) {
			this.jobMetaInformation = jobMetaInformation;
			return this;
		}

		public Builder exceptionStackDescription(String exceptionStackDescription) {
			this.exceptionStackDescription = exceptionStackDescription;
			return this;
		}

		public SchedulerProcessErrorLogDetail build() {
			return new SchedulerProcessErrorLogDetail(this);
		}
	}
}
