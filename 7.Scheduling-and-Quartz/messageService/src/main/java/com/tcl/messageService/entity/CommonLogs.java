package com.tcl.messageService.entity;

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

    @Id
    private int logID;

    private String taskType;
    private String targetName;
    private String taskName;
    private Date startTime;
    private Date nextStartTime;
}

