package com.savan.quartz.scheduler.vo;

public record JOBSchedulerExecutionMappingDetailDto (
    Long id,
	Integer executionSequence,
	Long processGroupDetailId,
    Integer chainedJobExecutionFaultTreatmentStrategy

) {}
