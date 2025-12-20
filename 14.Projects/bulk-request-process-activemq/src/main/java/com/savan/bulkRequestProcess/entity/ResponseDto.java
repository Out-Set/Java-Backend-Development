package com.savan.bulkRequestProcess.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String message;
    private String correlationId;
    private String status;
    private Object response;
}
