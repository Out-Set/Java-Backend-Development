package com.savan.multitransactions.controller;

import com.savan.multitransactions.service.EMService;
import com.savan.multitransactions.service.JPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mmt")
public class Controller {

    @Autowired
    private JPAService jpaService;

    @Autowired
    private EMService emService;

    // Create Through JPA
    @GetMapping("/jpa/create")
    public String saveJPARecords(){
        return jpaService.saveDataInBothDatabases();
    }

    // Create Through EM
    @GetMapping("/em/create")
    public String saveEMRecords(){
        return emService.executeNativeSqlQueries();
    }

    // Update Through EM
    @GetMapping("/em/update")
    public String updateThroughEm(){
        return emService.updateRowsThroughNq();
    }

    // Read Through EM
    @GetMapping("/em/read")
    public String readThroughEm(){
        return emService.readFromEm();
    }

    // Read Through JPA
    @GetMapping("/jpa/read")
    public String readThroughJpa(){
        return jpaService.readThroughJpa();
    }
}
