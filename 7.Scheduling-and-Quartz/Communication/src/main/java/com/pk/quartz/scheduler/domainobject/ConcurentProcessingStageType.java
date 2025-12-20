package com.pk.quartz.scheduler.domainobject;

import com.pk.integration.entity.GenericParameter;

import jakarta.persistence.Entity;

@Entity
public class ConcurentProcessingStageType extends GenericParameter {
	private static final long serialVersionUID = 1L;
	public static final String CANDIDATES_SELECTION = "CANDIDATES_SELECTION";
	public static final String CANDIDATE_PROCESSING = "CANDIDATE_PROCESSING";
	public static final String COMPLETION_CALLBACK = "COMPLETION_CALLBACK";
}
