package com.workflow.camunda.service.parallelAndSequential;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Arrays;
import java.util.List;

// sequential-multi-instance-process.bpmn
public class SequentialMultiInstanceProcess implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Inside SequentialMultiInstanceProcess class");
        List<String> list = Arrays.asList("foo", "bar", "test");
        delegateExecution.setVariable("list", list);
    }
}
