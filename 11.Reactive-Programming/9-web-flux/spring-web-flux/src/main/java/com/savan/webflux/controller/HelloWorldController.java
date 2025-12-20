package com.savan.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public List<String> helloWorldHandler() throws InterruptedException {
        List<String> returnList = new ArrayList<>();
        List<String> stringList = List.of("hello", "world", "hiii", "everyone");
        for (String str: stringList){
            System.out.println("element "+str+" processed by the thread: "+Thread.currentThread().getName());
            returnList.add(str);
            Thread.sleep(1000);
        }
        return returnList;
    }

    @GetMapping("/flux/hello")
    public Flux<String> helloWorldHandler2() throws InterruptedException {
        List<String> stringList = List.of("hello", "world", "hiii", "everyone");
        Flux<String> publisher = Flux.fromIterable(stringList).delayElements(Duration.ofSeconds(2)).log();
        return publisher;
    }

    @GetMapping("/mono/hello")
    public Mono<String> helloWorldHandler3() throws InterruptedException {
        Mono<String> publisher = Mono.just("Savan").log();
        return publisher;
    }

}
