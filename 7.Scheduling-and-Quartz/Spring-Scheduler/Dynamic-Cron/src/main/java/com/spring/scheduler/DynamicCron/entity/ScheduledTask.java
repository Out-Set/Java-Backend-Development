package com.spring.scheduler.DynamicCron.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduledTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String taskType;
    private String taskName;
    private String targetName;
    private String cronExpression;
    private String args;
    private String status;
    private String db;
}
