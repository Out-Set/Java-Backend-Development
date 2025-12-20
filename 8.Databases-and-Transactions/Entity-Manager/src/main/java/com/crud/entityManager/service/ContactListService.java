package com.crud.entityManager.service;

import com.crud.entityManager.entity.ContactList;
import com.crud.entityManager.repository.ContactListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ContactListService {

    @Autowired
    private ContactListRepo contactListRepo;

    // CRUD

    // create
    public String createContact(ContactList contactList) {
        contactListRepo.createContact(contactList);
        return "Contact Saved";
    }

    // insert manually
    public String createContact1(String name, int ph){
        contactListRepo.createContact1(name, ph);
        return "Saved!";
    }

    // read
    public List<ContactList> getContactList() {
        return contactListRepo.findAll();
    }

    public Object findById(Integer id) {
        return contactListRepo.findById(id);
    }

    public Object findByName(String name) {
        return contactListRepo.findByName(name);
    }

    // update
    public String update(Long id, ContactList contactList){
        return contactListRepo.updateEntity(id, contactList);
    }

    // delete
    public String delete(Integer id) {
        return contactListRepo.delete(id);
    }




    // Execute DB Procs
    public String executeProcedure1(){
        contactListRepo.executeProcedure1();
        return "Executed successfully!";
    }

    public String executeProcedure2(int id, String name){
        contactListRepo.executeProcedure2(id, name);
        return "Executed successfully!";
    }
}
