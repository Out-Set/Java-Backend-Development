package com.savan.quartz.scheduler.vo;

import java.util.List;

public record SchedulerProcessGroupDetailDto (
     Long id,
	 String processDisplayName,
	 String processDescription,
	 Short maximumExecutionFrequency,
	 Integer maintainExecutionLog,
	 Integer streamBased,
	 Boolean runInParallel,
	 Integer chainedJobExecutionFaultTreatmentStrategy,
	 Integer multiThreaded,
	 Long concurrentProcessingProfileTypeId,
	 Long scaleUpGridProcessTypeId,
     String transactionEvent,
	 List<Long> schedulerProcessDefinitionDetails
) {}
