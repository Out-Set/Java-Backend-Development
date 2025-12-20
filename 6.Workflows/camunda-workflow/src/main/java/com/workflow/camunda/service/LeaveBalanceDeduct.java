package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceDeduct implements JavaDelegate {

    public static final int LEAVE_BALANCE = 5;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("Leave deducted successfully");
    }
}
