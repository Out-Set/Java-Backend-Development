package com.savan.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class FunctionalController {

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        RequestPredicate request = RequestPredicates.GET("/functional/hello");
        HandlerFunction<ServerResponse> handlerFunction = new HandlerFunction<ServerResponse>() {
            @Override
            public Mono<ServerResponse> handle(ServerRequest request) {
                Flux<String> dataPublisher = Flux.just("hello", "world", "hii", "everyone").log();
                Mono<ServerResponse> serverResponse = ServerResponse
                        .ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(dataPublisher, String.class);
                return serverResponse;
            }
        };
        RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(request, handlerFunction);
        return routerFunction;
    }

    @Autowired
    private RouteHandler routeHandler;

    @Bean
    RouterFunction<ServerResponse> routerFunction2() {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/hello1"), request -> routeHandler.helloHandler())
                // .andRoute(RequestPredicates.GET("/functional/hello/{yourName}"), request -> routeHandler.pathVariableHandler(request));
                .andRoute(RequestPredicates.GET("/functional/hello/{yourName}"), routeHandler::pathVariableHandler);
    }
}
