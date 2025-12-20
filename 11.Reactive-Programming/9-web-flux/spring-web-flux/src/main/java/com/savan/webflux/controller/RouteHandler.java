package com.savan.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RouteHandler {

    public Mono<ServerResponse> helloHandler() {
        Flux<String> dataPublisher = Flux.just("hello", "world", "hii", "everyone")
                .delayElements(Duration.ofSeconds(2)).log();
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(dataPublisher, String.class);
    }

    public Mono<ServerResponse> pathVariableHandler(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("yourName");
        String response = "Your name is: "+name;
        Mono<String> dataPublisher = Mono.just(response).log();
        return ServerResponse
                .ok()
                .body(dataPublisher, String.class);
    }

}
