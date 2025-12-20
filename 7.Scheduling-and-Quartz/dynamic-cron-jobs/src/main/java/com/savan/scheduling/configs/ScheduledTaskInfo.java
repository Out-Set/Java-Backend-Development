package com.savan.scheduling.configs;

import java.util.concurrent.ScheduledFuture;

public class ScheduledTaskInfo {

    private final String taskId;
    private final ScheduledFuture<?> scheduledFuture;
    private final String cronExpression;

    public ScheduledTaskInfo(String taskId, ScheduledFuture<?> scheduledFuture, String cronExpression) {
        this.taskId = taskId;
        this.scheduledFuture = scheduledFuture;
        this.cronExpression = cronExpression;
    }

    public String getTaskId() {
        return taskId;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
