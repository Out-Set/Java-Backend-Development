package com.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Cron2Logs {

    // Logs
    @Id
    private int logID;
    private Date startTime;
    private Date nextStartTime;
}
