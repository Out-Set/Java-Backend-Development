package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeaveBalanceCheck implements JavaDelegate {

    public static final int LEAVE_BALANCE = 5;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if(LEAVE_BALANCE > 0){
            logger.info("Employee have leave balance");
        } else {
            logger.info("Employee do not have leave balance");
        }
    }
}
