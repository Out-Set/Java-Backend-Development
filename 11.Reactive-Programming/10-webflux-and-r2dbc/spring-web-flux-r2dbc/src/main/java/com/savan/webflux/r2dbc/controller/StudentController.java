package com.savan.webflux.r2dbc.controller;

import com.savan.webflux.r2dbc.entity.Student;
import com.savan.webflux.r2dbc.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/students", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents()
                .delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping(value = "/students/with-delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllStudentsWithDelay() {
        System.out.println("Threads which accepts the request: "+Thread.currentThread().getName());
        return studentService.getAllStudentsWithDelay()
                .doOnSubscribe(s ->
                        System.out.println("Subscribed on: " + Thread.currentThread().getName()))
                .doOnNext(student ->
                        System.out.println("Emitting value on: " + Thread.currentThread().getName()))
                .doOnComplete(() ->
                        System.out.println("Completed on: " + Thread.currentThread().getName()));
    }

    public Mono<ServerResponse> findAllStudents(ServerRequest serverRequest) {
        Flux<Student> allStudents = studentService.getAllStudents()
                .delayElements(Duration.ofSeconds(5)).log();
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(allStudents, Student.class);
    }
}
