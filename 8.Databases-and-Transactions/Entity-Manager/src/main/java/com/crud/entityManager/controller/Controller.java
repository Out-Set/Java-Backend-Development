package com.crud.entityManager.controller;

import com.crud.entityManager.entity.ContactList;
import com.crud.entityManager.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class Controller {

    @Autowired
    private ContactListService contactListService;

    // Perform CRUD
    // create
    @PostMapping("/create")
    public String createContact(@RequestBody ContactList contactList){
        return contactListService.createContact(contactList);
    }

    // insert manually
    @PostMapping("/{name}/{ph}")
    public String createContact1(@PathVariable("name") String name, @PathVariable("ph") int ph){
        contactListService.createContact1(name, ph);
        return "Saved!";
    }

    // read
    @GetMapping("/read")
    public List<ContactList> getContactList() {
        return contactListService.getContactList();
    }

    @GetMapping("/readById/{id}")
    public Object findById(@PathVariable Integer id) {
        return contactListService.findById(id);
    }

    @GetMapping("/readById")
    public Object findByIdParam(@RequestParam Integer id) {
        return contactListService.findById(id);
    }

    @GetMapping("/readByName/{name}")
    public Object findByName(@PathVariable String name) {
        return contactListService.findByName(name);
    }

    // update
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestBody ContactList contactList){
        return contactListService.update(id, contactList);
    }

    // delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return contactListService.delete(id);
    }





    // Execute Procs
    // No-Args proc
    @GetMapping("/executeProcedure1")
    public String executeProcedure(){
        return contactListService.executeProcedure1();
    }

    // With args proc
    @GetMapping("/executeProcedure2")
    public String executeProcedure2(@RequestParam int id, @RequestParam String name){
        return contactListService.executeProcedure2(id, name);
    }
}
