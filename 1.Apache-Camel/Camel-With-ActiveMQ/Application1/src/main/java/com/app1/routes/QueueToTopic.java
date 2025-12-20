package com.app1.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class QueueToTopic extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // log the queues, which has subscribed the topic-1
        from("activemq:queue:queue-6").log("Message from queue-6: ${body}");
        from("activemq:queue:queue-7").log("Message from queue-7: ${body}");
        from("activemq:queue:queue-8").log("Message from queue-8: ${body}");

        from("activemq:topic:topic-1").log("Message from topic-1: ${body}");
    }
}
