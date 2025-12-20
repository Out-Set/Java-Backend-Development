package com.savan.quartz.comm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "communication_data")
public class CommData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String subject;
    private String recipient;
    private String communicationType;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(length = 512)
    private String responseBody;

    private LocalDateTime creationTimeStamp;
    private LocalDateTime requestTimeStamp;
    private LocalDateTime responseTimeStamp;

}
