package com.app1.routes;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class QueueToFile extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue-1").log("${body}")
                .process(exchange -> {
                    Message input = exchange.getIn();
                    String data = input.getBody(String.class);

                    Message output = exchange.getMessage();
                    output.setBody(data);
                })
                .to("file://C:\\Users\\admin\\Desktop\\FileRouter\\Output?fileName=userDtl.json")
                .to("activemq:queue-2");
    }
}
