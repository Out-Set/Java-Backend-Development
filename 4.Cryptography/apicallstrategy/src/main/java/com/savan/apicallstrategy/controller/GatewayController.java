package com.savan.apicallstrategy.controller;

import com.savan.apicallstrategy.util.CryptoGatewayService;
import com.savan.apicallstrategy.model.CryptoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private CryptoGatewayService cryptoGatewayService;

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody CryptoRequest request) {
        try {
            Object result = cryptoGatewayService.process(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}