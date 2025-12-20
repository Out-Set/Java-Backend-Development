package com.savan.springboot_profiles.controller;

import com.savan.springboot_profiles.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-slave")
public class TestController {

    @Autowired
    private TestService testService;

    // Master Controller
    @GetMapping("/master")
    public ResponseEntity<String> fromMaster(){
        String resp = testService.getMasterResponse();
        if(resp.equals("Profile-Not-Active")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    // Slave Controller
    @GetMapping("/slave")
    public ResponseEntity<String> fromSlave(){
        String resp = testService.getSlaveResponse();
        if(resp.equals("Profile-Not-Active")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
