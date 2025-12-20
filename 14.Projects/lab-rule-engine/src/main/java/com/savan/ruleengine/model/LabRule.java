package com.savan.ruleengine.model;

import lombok.Data;

@Data
public class LabRule {
    private String id;
    private String testCode;
    private String expression;
    private String action;    // e.g., AUTO_APPROVE, REVIEW
    private Boolean active;
    private Integer priority;
}
