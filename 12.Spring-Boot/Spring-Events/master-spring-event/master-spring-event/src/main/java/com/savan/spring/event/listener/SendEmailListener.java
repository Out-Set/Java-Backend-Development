package com.savan.spring.event.listener;

import com.savan.spring.event.event.TransactionFailureEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener {

    @Async
    @EventListener
    public void emailListener(TransactionFailureEvent event) {
        System.out.println("SendEmailListener ->  inside emailListener() starting... ");
        try {
            Thread.sleep(3000);
            System.out.println("Sending an email with body \n"
                    + "Hi "+event.getName()+", transaction failed for the amount: "+event.getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SendEmailListener ->  inside emailListener() ending... ");
    }
}
