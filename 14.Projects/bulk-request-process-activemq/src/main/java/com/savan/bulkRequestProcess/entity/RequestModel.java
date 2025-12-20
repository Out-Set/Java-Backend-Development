package com.savan.bulkRequestProcess.entity;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestModel {

    private String correlationId;
    private String sourceSystem;
    private String apiUrl;
    private String apiName;
    private Map<String, Object> headers;
    private Object requestBody;
    private String httpMethod;
    private String contentType;
}
