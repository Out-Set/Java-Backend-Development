package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

// business-rule-task.bpmn
public class InputOutputParameter implements JavaDelegate {

    private Expression qaUrl;

    public void setQaUrl(Expression qaUrl) {
        this.qaUrl = qaUrl;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Input variable: "+delegateExecution.getVariable("local-gender"));
        System.out.println("Output variable: "+delegateExecution.getVariable("global-gender"));

        // Print Field injection
        if (qaUrl != null) {
            System.out.println("Field-Injection qaUrl: " + qaUrl.getValue(delegateExecution));
        } else {
            System.out.println("Field-Injection qaUrl was not injected!");
        }
    }
}
