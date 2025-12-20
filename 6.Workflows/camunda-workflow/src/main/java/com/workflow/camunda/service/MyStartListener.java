package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class MyStartListener implements ExecutionListener {
  @Override
  public void notify(DelegateExecution execution) throws Exception {
    System.out.println("Process started: " + execution.getProcessInstanceId());
  }
}
