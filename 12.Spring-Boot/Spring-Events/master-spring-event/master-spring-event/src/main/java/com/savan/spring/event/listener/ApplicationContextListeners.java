package com.savan.spring.event.listener;

import org.springframework.context.event.*;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextListeners {

    @EventListener
    public void onContextRefreshedEvent(ContextRefreshedEvent event) {
        System.out.println("application initialized or refreshed... ");
        System.out.println(event);
    }

    @EventListener
    public void onContextStartedEvent(ContextStartedEvent event) {
        System.out.println("application initialized with start()... ");
        System.out.println(event);
    }

    @EventListener
    public void onContextStoppedEvent(ContextStoppedEvent event) {
        System.out.println("application stopped with stop()... ");
        System.out.println(event);
    }

    @EventListener
    public void onContextClosedEvent(ContextClosedEvent event) {
        System.out.println("application closed with close()... ");
        System.out.println(event);
    }
}
