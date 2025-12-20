package com.app1.consumer;

import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "queue-6")
    public void consumeFromQueue6(String message){
        System.out.println("Received Messages form Queue-6: " + message);
    }

    @JmsListener(destination = "queue-7")
    public void consumeFromQueue7(String message){
        System.out.println("Received Messages form Queue-7: " + message);
    }

    @JmsListener(destination = "queue-8")
    public void consumeFromQueue8(String message){
        System.out.println("Received Messages form Queue-8: " + message);
    }

    @SendTo("topic-1")
    public String receiveMessage(TextMessage message) {
        // Process the message here
        return "Got the Message!";
    }
}