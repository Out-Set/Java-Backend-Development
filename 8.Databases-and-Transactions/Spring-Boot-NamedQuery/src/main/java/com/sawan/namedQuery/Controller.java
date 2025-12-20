package com.sawan.namedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Repo repository;

    @GetMapping("/data")
    public List<Entity> data(){
        return repository.findData();
    }

    @GetMapping("/data/{id}")
    public List<Entity> dataById(@PathVariable int id){
        return repository.findDataById(id);
    }

    @GetMapping("/data/idAndName")
    public Entity findDataByIdAndName(@RequestParam int id, @RequestParam String name){
        return repository.findDataByIdAndName(id, name);
    }
}
