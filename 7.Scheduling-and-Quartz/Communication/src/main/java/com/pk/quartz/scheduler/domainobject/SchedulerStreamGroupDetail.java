package com.pk.quartz.scheduler.domainobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.BaseEntity;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SCHED_STREAM_GRP_DTL", indexes = {
		@Index(name = "SCHED_STREAM_GRP_DTL_IDX1", columnList = "PROCESS_GRP_DTL_ID")})
public class SchedulerStreamGroupDetail extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	@Column(name = "STREAM_SOURCE_PATH", length = 255)
	private String streamSourcePath;
	@Column(name = "STREAM_HISTORY_PATH", length = 255)
	private String streamHistoryPath;
	@Column(name = "FILE_NAME_LOOKUP_PATTERN", length = 255)
	private String fileNameLookupPattern;
	@Column(name = "JOB_DATA", length = 255)
	private String jobData;
	@OneToOne
	@JoinColumn(name = "PROCESS_GRP_DTL_ID")
	private SchedulerProcessGroupDetail schedulerProcessGroupDetail;

	public String getStreamSourcePath() {
		return this.streamSourcePath;
	}

	public void setStreamSourcePath(String streamSourcePath) {
		this.streamSourcePath = streamSourcePath;
	}

	public String getStreamHistoryPath() {
		return this.streamHistoryPath;
	}

	public void setStreamHistoryPath(String streamHistoryPath) {
		this.streamHistoryPath = streamHistoryPath;
	}

	public String getFileNameLookupPattern() {
		return this.fileNameLookupPattern;
	}

	public void setFileNameLookupPattern(String fileNameLookupPattern) {
		this.fileNameLookupPattern = fileNameLookupPattern;
	}

	public String getJobData() {
		return this.jobData;
	}

	public void setJobData(String jobData) {
		this.jobData = jobData;
	}

	public SchedulerProcessGroupDetail getSchedulerProcessGroupDetail() {
		return this.schedulerProcessGroupDetail;
	}

	public void setSchedulerProcessGroupDetail(SchedulerProcessGroupDetail schedulerProcessGroupDetail) {
		this.schedulerProcessGroupDetail = schedulerProcessGroupDetail;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}