package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// message-and-script-task.bpmn
public class ScriptTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // Print out-put generated through script-task
        System.out.println("Output is: "+delegateExecution.getVariable("output"));
    }
}
