package com.savan.spring.event.publisher;

import com.savan.spring.event.event.TransactionFailureEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Gpay {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void sendMoney(String name, double amount, boolean condition) {
        try {
            if (condition) throw new RuntimeException("Transaction failed... ");
            System.out.println("Hi "+name);
            System.out.println("Sending amount "+amount+" is successful!");
        } catch (Exception e) {
            eventPublisher.publishEvent(new TransactionFailureEvent(name, amount));
            e.printStackTrace();
        }
    }
}
