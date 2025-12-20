package com.workflow.camunda.config;

import org.apache.camel.CamelContext;
import org.camunda.bpm.camel.common.CamelServiceCommonImpl;
import org.camunda.bpm.camel.component.CamundaBpmComponent;
import org.camunda.bpm.camel.spring.CamelServiceImpl;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelBootStrap {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProcessEngine processEngine;

	@Bean(name = "camunda-bpm")
	public CamundaBpmComponent camundaBpmComponent() {
		CamundaBpmComponent camundaComponent = new CamundaBpmComponent();
		camundaComponent.setProcessEngine(processEngine);
		camundaComponent.setCamelContext(camelContext);
		// Assuming processEngine bean is defined elsewhere
		return camundaComponent;
	}

	@Bean(name = "camel")
	public CamelServiceCommonImpl camelService() {
		CamelServiceImpl camelService = new CamelServiceImpl();
		camelService.setProcessEngine(processEngine);
		camelService.setCamelContext(camelContext);
		return camelService;
	}

	@Autowired(required = false)
	public CamelBootStrap(CamelContext camelContext) {
		System.out.println("CamelContext: " + (camelContext != null ? "Loaded Successfully!" : "Not Found"));
	}

}