package com.app2.producer;

import jakarta.jms.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    @GetMapping("/{message}")
    public String sendMessageToTopic(@PathVariable("message") String message) {
        jmsTemplate.convertAndSend("topic-1", message);
        return "Published Successfully to topic-1";
    }
}
