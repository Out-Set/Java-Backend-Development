package com.example.JDBC.SpringwebwithJDBC.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JDBC.SpringwebwithJDBC.DAOs.Person;
import com.example.JDBC.SpringwebwithJDBC.DBManager.DBOperations;
import com.example.JDBC.SpringwebwithJDBC.Requests.CreateRequest;

@RestController
public class PersonController {

    // calling to create a table
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public void createTable(@RequestParam(value = "name") String name) throws SQLException{
        DBOperations.createTable(name);
    }

    // or @GetMapping("/getPerson"), both are the same
    
    // calling to insert records into table
    @RequestMapping(value = "/insertPerson", method = RequestMethod.POST)
    public void insertPerson(@RequestBody CreateRequest request) throws SQLException{
        DBOperations.insertPerson(request);
    }

    // calling dao to get object from DB
    @GetMapping(value = "/getPersons")
    public List<Person> getPersons() throws SQLException {
        return DBOperations.getPersons();
    }

    // To search a record by id
    @GetMapping("/getPersonById/{id}")
    public List<Person> getPersonById()  {
        // return DBOperations.getPersonById();
        return null;
    }

}
