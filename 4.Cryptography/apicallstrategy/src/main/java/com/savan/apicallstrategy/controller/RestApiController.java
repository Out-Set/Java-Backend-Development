package com.savan.apicallstrategy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/timeout")
    public ResponseEntity<String> timeout() throws InterruptedException {
        // Simulate hang by sleeping indefinitely
        Thread.sleep(Long.MAX_VALUE);
        return ResponseEntity
                .internalServerError()
                .body("Server timeout occurred.");
    }

}
