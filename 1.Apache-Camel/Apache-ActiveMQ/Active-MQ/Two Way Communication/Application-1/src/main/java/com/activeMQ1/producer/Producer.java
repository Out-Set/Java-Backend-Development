package com.activeMQ1.producer;

import com.activeMQ1.entity.Entity;
import com.activeMQ1.repository.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Component
@RestController
@RequestMapping("rest/publish")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @GetMapping("/{message}")
    public String publish(@PathVariable("message") final String message){
        jmsTemplate.convertAndSend(queue, message);
        return "Published Successfully";
    }

    @Autowired
    private Repo repo;

//    @JmsListener(destination = "RequestQueue")
//    @SendTo("ResponseQueue")
//    public String processGetRequest(String request) throws JsonProcessingException {
//        // Process the request and generate a response
//        List<Entity> data =  repo.findAll();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String resp = objectMapper.writeValueAsString(data);
//
//        System.out.println(resp);
//        return resp;
//    }

    @JmsListener(destination = "RequestQueue")
    @SendTo("ResponseQueue")
    public String processPostRequest(String entity) throws JsonProcessingException {
        System.out.println(entity);
        // Process the request and generate a response
        ObjectMapper objectMapper = new ObjectMapper();
        Entity data = objectMapper.readValue(entity, Entity.class);
        repo.save(data);

        return entity;
    }
}

