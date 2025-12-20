package com.savan.spring.event;

import com.savan.spring.event.config.AppConfig;
import com.savan.spring.event.publisher.Gpay;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.start();

        Gpay bean = context.getBean("gpay", Gpay.class);
        bean.sendMoney("Savan", 5000, false);
        bean.sendMoney("Savan", 5000, true);

        context.stop();
        context.close();
    }
}