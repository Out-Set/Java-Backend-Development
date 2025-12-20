package com.savan.webflux.r2dbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private StudentController studentController;

    @Bean
    RouterFunction<ServerResponse> studentRouterConfig(){
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/students"), studentController::findAllStudents);
    }
}
