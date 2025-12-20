package com.savan.spring.event.listener;

import com.savan.spring.event.event.TransactionFailureEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SendSMSListener {

    @EventListener
    public void smsListener(TransactionFailureEvent event) {
        System.out.println("Sending an sms with body \n"
            + "Hi "+event.getName()+", transaction failed for the amount: "+event.getAmount());
    }
}
