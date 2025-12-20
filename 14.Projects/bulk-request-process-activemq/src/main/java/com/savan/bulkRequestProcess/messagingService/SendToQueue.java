package com.savan.bulkRequestProcess.messagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

/*
user.request.queue=user-request-queue
process.request.queue=process-request-queue
*/

@Component
public class SendToQueue {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${user.request.queue}")
    private String userRequestQueue;

    @Value("${process.request.queue}")
    private String processRequestQueue;

    // To User Request Queue
    public void toUserRequestQueue(String message) {
        jmsTemplate.send(userRequestQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message.toString());
                return textMessage;
            }
        });
    }

    // To Process Request Queue
    public void toProcessRequestQueue(String message) {
        jmsTemplate.send(processRequestQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message.toString());
                return textMessage;
            }
        });
    }
}
