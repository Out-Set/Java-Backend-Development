package com.savan.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(10000);
        return "Hello World!";
    }
}
