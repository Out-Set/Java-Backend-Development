package com.workflow.camunda.groovy;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import org.apache.camel.Exchange;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.codehaus.groovy.control.CompilerConfiguration;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroovyScripting {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CompilerConfiguration groovyCompilerConfiguration;

    public Object executeScript(String script, Map<String, Object> runtimeVariables, Exchange exchange) {
        // Create a new Binding
        Binding binding = new Binding();
        binding.setVariable("variableService", runtimeService);

        String processInstanceId= exchange.getProperty("CamundaBpmProcessInstanceId",String.class);
        binding.setVariable("processInstanceId", processInstanceId);

        // Add any runtime variables
        if (runtimeVariables != null) {
            runtimeVariables.forEach(binding::setVariable);
        }
        GroovyShell groovyShell = new GroovyShell(binding, groovyCompilerConfiguration);
        try {
            return groovyShell.evaluate(script);
        } catch (Exception e) {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE,"500");
            throw new RuntimeException("Script execution blocked or failed: " + e.getMessage(), e);
        }
    }

    public void executeGroovyScript(Exchange exchange, String script) {

        String contentType = exchange.getIn().getHeader(Exchange.CONTENT_TYPE, String.class);
        String payload = exchange.getIn().getBody(String.class);
        System.out.println("ContentType: "+contentType+" before Script Execution: "+payload);

        JsonSlurper jsonSlurper = new JsonSlurper();
        Map<String, Object> bindingVariables = new HashMap<>();

        var input = jsonSlurper.parseText(payload);
        System.out.println("Converted Map: "+ input);
        bindingVariables.put("input", input);

        var output = executeScript(script, bindingVariables, exchange);

        if (!(output instanceof String)) {
            output = JsonOutput.toJson(output);
        }

        System.out.println("Output After Script Execution: "+ output);
        exchange.getIn().setBody(output);
    }

}
