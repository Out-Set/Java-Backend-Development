package com.workflow.camunda.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.camunda.groovy.GroovyScripting;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.camunda.bpm.camel.component.CamundaBpmConstants.EXCHANGE_HEADER_PROCESS_INSTANCE_ID;

// services-workflow.bpmn

@Component
public class WorkflowCallRoute extends RouteBuilder {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private GroovyScripting groovyScripting;

    private final String requestConversionScript = """
            
            def requestHeaders = input.RequestHeaders
            def correlationId = requestHeaders["x-correlation-id"]
            def conversationId = correlationId + "-4spt10dt"
            requestHeaders["conversationId"] = conversationId
            
            def transformedInput = [
            referenceID:input.referenceID,
            product:input.product,
            dueDay:input.loanDetails.repaymentSchedule.dueDay,
            loanDetails:[
                agreementDate:input.loanDetails.agreementDate,
                anchorTypeCode:input.loanDetails.repaymentSchedule.anchorTypeCode
                ]
            ]
            return transformedInput
            """;

    private final String responseConversionScript = """
            def transformedOutput = [
                type:input.Result_servicesDemoRoute.type,
                priority:input.Result_servicesDemoRoute.priority,
                scripting:input.Result_servicesDemoRoute.scripting,
            	data:[
            		output:input.Result_servicesDemoRoute.output,
            		message:input.Result_servicesDemoRoute.message,
            		process:input.Result_servicesDemoRoute.process,
            		statusCode:input.Result_servicesDemoRoute.statusCode
            	]
            ]
            return transformedOutput
            """;

    @Override
    public void configure() throws Exception {


        // To call workflow: services-workflow.bpmn
        rest("/bpm/call").get()
                .to("direct:start-workflow");

        from("direct:start-workflow")
                .process(exchange -> {
                    // Execute Request-Conversion Groovy-Script
                    groovyScripting.executeGroovyScript(exchange, requestConversionScript);

                    System.out.println("Before Workflow Call ...");
                    Map<String, Object> body = exchange.getIn().getBody(Map.class);
                    Map<String,Object> headers =  new HashMap<>(exchange.getIn().getHeaders()) ;
                    Map<String, Object> requestPayload = new HashMap<>();
                    headers.remove(Exchange.HTTP_SERVLET_REQUEST);
                    headers.remove(Exchange.HTTP_SERVLET_RESPONSE);
                    requestPayload.put("RequestPayload", body);
                    requestPayload.put("RequestHeaders", headers);
                    exchange.getIn().setBody(requestPayload);
                })
                .log("Current Body Before Workflow Call: ${body}")
                .to("camunda-bpm:start?processDefinitionKey=services-workflow&copyBodyAsVariable=RequestPayload&copyProperties=true")
                .log("Started Camunda process with ID: ${header.CamundaBpmProcessInstanceId}")
                .process(exchange -> {
                    String processInstanceId = exchange.getProperty(EXCHANGE_HEADER_PROCESS_INSTANCE_ID, String.class);
                    System.out.println("Process instance id: "+processInstanceId);

                    VariableMap variableMap = Variables.createVariables();

                    // For runtimeService
                    /*
                    List<VariableInstance> variables = runtimeService.createVariableInstanceQuery()
                            .processInstanceIdIn(processInstanceId).variableNameLike("%Result_%")
                            .list();
                    for (VariableInstance variable : variables) {
                        variableMap.put(variable.getName(), variable.getValue());
                    }
                     */

                    // For historyService
                    List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery()
                            .processInstanceId(processInstanceId)
                            .variableNameLike("Result_%")
                            .list();

                    for (HistoricVariableInstance variable : variables) {
                        variableMap.put(variable.getName(), variable.getValue());
                    }

                    System.out.println("VariableMap Contains: "+variableMap);
                    ObjectMapper mapper = new ObjectMapper();
                    exchange.getIn().setBody(mapper.writeValueAsString(variableMap));

                    // Execute Response-Conversion Groovy-Script
                    groovyScripting.executeGroovyScript(exchange, responseConversionScript);

                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
                    exchange.getIn().setHeader("Content-Type", "application/json");
                })
                .log("Process Finished with body: ${body}")
                .log("Process Finished with headers: ${headers}");


        // A demo route for: services-workflow.bpmn
        from("direct:servicesDemoRoute")
                .process(exchange -> {
                    Map<String, Object> sampleResp = new HashMap<>();
                    sampleResp.put("process", "work-flow execution");
                    sampleResp.put("statusCode", 200);
                    sampleResp.put("type", "dynamic");
                    sampleResp.put("priority", "high");
                    sampleResp.put("scripting", true);
                    sampleResp.put("output", "needed");
                    sampleResp.put("message", "Route servicesDemoRoute executed successfully ...");
                    exchange.getMessage().setBody(sampleResp);
                })
                .log("Route servicesDemoRoute executed successfully ...");

    }

}
