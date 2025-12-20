package com.savan.ruleengine.model;

import lombok.Data;

@Data
public class LabTest {
    private String patientId;
    private String testCode;
    private Double resultValue;
    private String unit;
    private String status;
    private Boolean autoApproved;
    private String approvedBy;
}
