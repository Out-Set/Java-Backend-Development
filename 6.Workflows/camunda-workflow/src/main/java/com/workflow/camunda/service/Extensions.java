package com.workflow.camunda.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;

// extensions.bpmn
// Adding local properties(key:value) to a particular task
public class Extensions implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ServiceTask serviceTask = (ServiceTask) delegateExecution.getBpmnModelElementInstance();
        CamundaProperties camundaProperties = serviceTask.getExtensionElements().getElementsQuery().filterByType(CamundaProperties.class).singleResult();
        for (CamundaProperty camundaProperty: camundaProperties.getCamundaProperties()){
            System.out.println("Name: "+camundaProperty.getCamundaName());
            System.out.println("Value: "+camundaProperty.getCamundaValue());
        }
    }
}
