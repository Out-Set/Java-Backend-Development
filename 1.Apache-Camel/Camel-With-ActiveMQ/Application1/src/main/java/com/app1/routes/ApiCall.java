package com.app1.routes;

import com.app1.service.ApiService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

//@Component
public class ApiCall extends RouteBuilder {

    @Autowired
    private ApiService apiService;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("/apiCall")
                .get()
                .to("direct:get-users");

        from("direct:get-users")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Object resp = apiService.fetchApiDataWithRetry(apiService);
                        System.out.println("resp ="+resp);
                        exchange.getIn().setBody(resp);
                    }
                })
                .to("activemq:queue-10")
                .log("Sent to activeMQ queue-10 ...")
                .log("Api call data: ${body}");

    }
}