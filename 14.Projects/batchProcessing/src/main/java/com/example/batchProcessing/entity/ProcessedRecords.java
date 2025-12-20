package com.example.batchProcessing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedRecords {

    @Id
    private Long recordId;
    private LocalDateTime processedAt;
}
