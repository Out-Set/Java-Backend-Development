package com.app1.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TopicToManyQueue extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Crete topic and let many queues subscribe this topic
        from("activemq:topic:topic-1")
                .to("activemq:queue:queue-6")
                .to("activemq:queue:queue-7")
                .to("activemq:queue:queue-8");
    }
}
