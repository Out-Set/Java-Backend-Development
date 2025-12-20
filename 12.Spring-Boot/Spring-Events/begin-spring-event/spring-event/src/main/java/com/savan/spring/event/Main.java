package com.savan.spring.event;

import com.savan.spring.event.config.AppConfig;
import com.savan.spring.event.publisher.ZeeCafePublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ZeeCafePublisher bean = context.getBean("zeeCafePublisher", ZeeCafePublisher.class);
        bean.streamBigBangTheory("EP - 004");
        bean.streamComedyCircus("EP - 007");
    }
}