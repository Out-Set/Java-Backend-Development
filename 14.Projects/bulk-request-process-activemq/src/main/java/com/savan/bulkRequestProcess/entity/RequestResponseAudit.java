package com.savan.bulkRequestProcess.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "bulk_req_resp_service_audit")
public class RequestResponseAudit {

    @Id
    private String correlationId;

    private String apiName;
    private String apiUrl;

    @Column(columnDefinition = "TEXT")
    private String requestString;
    private LocalDateTime requestTimeStamp;

    @Column(columnDefinition = "TEXT")
    private String responseString;
    private LocalDateTime responseTimeStamp;

    private String sourceSystem;
    private Boolean processStatus;
}
