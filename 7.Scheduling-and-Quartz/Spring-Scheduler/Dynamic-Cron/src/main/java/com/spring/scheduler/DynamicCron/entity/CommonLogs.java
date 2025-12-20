package com.spring.scheduler.DynamicCron.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CommonLogs {

    // Common Logs Table
    @Id
    private int logID;

    private String taskType;
    private String taskName;
    private String targetName;
    private Date startTime;
    private Date nextStartTime;
    private String executionStatus;
}
