package com.tcl.messageService.entity;

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
    private String target;
    private String cronExpression;

    @Column(length = 512)
    private String args;

    private String status;
    private String db;
}
