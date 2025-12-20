package com.workflow.camunda.service.async;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// asynchronous-test.bpmn
public class AsyncPrintAgeTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Age is: "+delegateExecution.getVariable("age"));

        // Trying to get a variable and typecast it which does not exist,
        // in order to understand async-before & async-after
        System.out.println("Age is: "+delegateExecution.getVariable("name").toString());
    }
}
