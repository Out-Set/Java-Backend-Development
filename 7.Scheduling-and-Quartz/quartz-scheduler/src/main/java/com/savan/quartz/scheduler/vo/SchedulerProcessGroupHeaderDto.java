package com.savan.quartz.scheduler.vo;

import java.util.Set;

public record SchedulerProcessGroupHeaderDto (
    Long id,
    String processGroupDisplayName,
    String processGroupDescription,
    String transactionEvent,
    String module,
    Integer executionControlState,
    Set<Long> schedulerProcessGroupDetailIds
) {}
