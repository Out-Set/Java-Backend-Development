package com.security.keycloak.controllers;

import java.util.List;

import org.keycloak.common.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.service.ApisService;

@RestController
@CrossOrigin("*")
public class ApisController {

    @Autowired
    private ApisService apisService;

    @PostMapping("/createApi")
    public String createApi(@RequestBody Apis apis){
        return apisService.createApi(apis);
    }

    @PostMapping("/createManyApis")
    public String createManyApis(@RequestBody List<Apis> apis){
        return apisService.createManyApis(apis);
    }

    @GetMapping("/getAllApi")
//    @PreAuthorize("hasRole('default-roles-google')")
    public List<Apis> getAllApi() {
    	return apisService.getAllApi();
    }
    
    @GetMapping("/getApiDetails")
//    @PreAuthorize("hasRole('default-roles-google')")
    public Object getApiDetails(@RequestHeader("Authorization") String authorizationHeader) throws VerificationException {
    	return apisService.getAllApiDetails(authorizationHeader);
    	//return clientService.getApiDetails();
    }
    
    @GetMapping("/readApi/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Apis readApi(@PathVariable int id){
        return apisService.readApi(id);
    }

    @PostMapping("/updateApi")
    public String updateApi(@RequestBody Apis apis){
        return apisService.updateApi(apis);
    }

    @GetMapping("/deleteApi/{id}")
    public String deleteApi(@PathVariable int id){
        return apisService.deleteApi(id);
    }
}
