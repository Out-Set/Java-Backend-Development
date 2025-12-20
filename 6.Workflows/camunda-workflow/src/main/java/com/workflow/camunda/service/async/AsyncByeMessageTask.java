package com.workflow.camunda.service.async;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// asynchronous-test.bpmn
public class AsyncByeMessageTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Bye bye workflow!!");
    }
}
