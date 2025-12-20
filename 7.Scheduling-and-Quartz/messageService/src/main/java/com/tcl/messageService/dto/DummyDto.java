package com.tcl.messageService.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DummyDto {

    private String logId;
    private String reqTimeStamp;
    private String sourceName;
    private String serviceName;
    private String fetchStatus;
}
