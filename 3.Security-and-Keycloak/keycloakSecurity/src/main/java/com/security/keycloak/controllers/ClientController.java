package com.security.keycloak.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.keycloak.entity.Client;
import com.security.keycloak.service.ClientService;

@RestController
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public String createClient(@RequestBody Client client){
        return clientService.createClient(client);
    }

    @PostMapping("/createManyClient")
    public String createManyClient(@RequestBody List<Client> client){
        return clientService.createManyClient(client);
    }

    @GetMapping("/readClient/{id}")
    public Client readClient(@PathVariable int id){
        return clientService.readClient(id);
    }
    
    @GetMapping("/clients")
    @PreAuthorize("hasRole('FETCH_CLIENTS')")
    public ResponseEntity<List<Client>> fetchAllClients() {
    	List<Client> clientList = clientService.getAllClient();
    	if(clientList != null) {
    		return ResponseEntity.status(HttpStatus.OK).body(clientList);
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }

    @PostMapping("/updateClient")
    public String updateClient(@RequestBody Client client){
        return clientService.updateClient(client);
    }

    @GetMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable int id){
        return clientService.deleteClient(id);
    }
}
