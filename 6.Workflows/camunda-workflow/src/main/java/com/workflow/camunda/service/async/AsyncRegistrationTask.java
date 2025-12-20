package com.workflow.camunda.service.async;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// asynchronous-test.bpmn
public class AsyncRegistrationTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Register to workflow design!!");
    }
}
