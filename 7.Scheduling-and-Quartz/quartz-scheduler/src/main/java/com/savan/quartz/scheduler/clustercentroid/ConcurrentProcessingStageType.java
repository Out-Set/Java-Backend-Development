package com.savan.quartz.scheduler.clustercentroid;

import com.savan.quartz.entity.GenericParameter;
import jakarta.persistence.Entity;

@Entity
public class ConcurrentProcessingStageType extends GenericParameter {

	private static final long serialVersionUID = 1L;
	public static final String CANDIDATES_SELECTION = "CANDIDATES_SELECTION";
	public static final String CANDIDATE_PROCESSING = "CANDIDATE_PROCESSING";
	public static final String COMPLETION_CALLBACK = "COMPLETION_CALLBACK";
}