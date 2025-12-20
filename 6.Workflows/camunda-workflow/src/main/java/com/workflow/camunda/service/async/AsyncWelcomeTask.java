package com.workflow.camunda.service.async;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// asynchronous-test.bpmn
public class AsyncWelcomeTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Welcome to workflow design!!");
    }
}
