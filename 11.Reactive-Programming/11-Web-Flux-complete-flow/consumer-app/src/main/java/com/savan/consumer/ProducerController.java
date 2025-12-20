package com.savan.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class ProducerController {

    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    @GetMapping("/consume")
    public Mono<String> consumeData() {
        System.out.println("executing consumeData() method... ");
        // rest call here of producer app
        Mono<String> response = webClient.get().uri("hello").retrieve().bodyToMono(String.class);
        return response;
    }

    @GetMapping("/consumer")
    public Mono<String> consumer() {
        System.out.println("executing consumer() method... ");
        return Mono.just("consumer data... ").delayElement(Duration.ofSeconds(2));
    }

    @GetMapping("/simple")
    public Mono<String> simpleCall() {
        System.out.println("executing simpleCall() method... ");
        return Mono.just("simple data... ");
    }
}
