package com.tcl.messageService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomCommAuditDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime creationTimeStamp;
    private String status;

    @Column(length = 512)
    private String requestBody;

    @Column(length = 512)
    private String responseBody;

    private LocalDateTime requestTimeStamp;
    private LocalDateTime responseTimeStamp;

    @Column(length = 512)
    private String messageBody;

    private String communicationType;
    private String communicationTypeName;

    public CustomCommAuditDtl(LocalDateTime creationTimeStamp, String status, String requestBody, String responseBody, LocalDateTime requestTimeStamp, LocalDateTime responseTimeStamp, String messageBody, String communicationType, String communicationTypeName) {
        this.creationTimeStamp = creationTimeStamp;
        this.status = status;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.requestTimeStamp = requestTimeStamp;
        this.responseTimeStamp = responseTimeStamp;
        this.messageBody = messageBody;
        this.communicationType = communicationType;
        this.communicationTypeName = communicationTypeName;
    }
}
