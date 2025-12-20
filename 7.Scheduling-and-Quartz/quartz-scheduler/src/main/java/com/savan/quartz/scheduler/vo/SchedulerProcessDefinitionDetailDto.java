package com.savan.quartz.scheduler.vo;

public record SchedulerProcessDefinitionDetailDto (
	Long id,
	String beanId,
	String className,
	String methodToExecute,
    Long concurrentProcessingStageTypeId
) {}