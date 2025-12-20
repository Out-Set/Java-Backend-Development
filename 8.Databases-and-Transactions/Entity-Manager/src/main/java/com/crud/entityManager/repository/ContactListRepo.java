package com.crud.entityManager.repository;

import com.crud.entityManager.entity.ContactList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactListRepo {

    @Autowired  // or @PersistenceContext
    private EntityManager entityManager;

    // CRUD-Operations
    // 1:Create
    @Transactional
    public void createContact(ContactList contact) {
        entityManager.persist(contact);
    }

    // insert manually
    @Transactional
    public void createContact1(String name, int ph) {
        String nativeQuery = "insert into contact_list(name, ph) values(?, ?)";
        Query query = entityManager.createNativeQuery(nativeQuery, ContactList.class);
        query.setParameter(1, name);
        query.setParameter(2, ph);
        query.executeUpdate();
    }

    // 2:Read
    public List findAll() {
        String nativeQuery = "SELECT * FROM contact_list";
        Query query = entityManager.createNativeQuery(nativeQuery, ContactList.class);

        return query.getResultList();
    }

    public Object findById(Integer id) {
        ContactList result = entityManager.find(ContactList.class, id);
        if (result != null) {
            return result;
        } else {
            return "Entity with the given ID not found!";
        }
    }

    public Object findByName(String name) {
        String nativeQuery = "SELECT * FROM contact_list where name= :name";
        Query query = entityManager.createNativeQuery(nativeQuery, ContactList.class);
        query.setParameter("name", name);

        // or
//         String nativeQuery = "SELECT * FROM contact_list where name= ?";
//         Query query = entityManager.createNativeQuery(nativeQuery, ContactList.class);
//         query.setParameter(1, name);

        try{
            return query.getSingleResult();
        } catch (Exception e){
            return "Entity with the given name not found!";
        }
    }

    // 3:Update
    @Transactional
    public String updateEntity(Long id, ContactList contactList) {
        // Find the entity by its ID
        ContactList existingEntity = entityManager.find(ContactList.class, id);

        if (existingEntity != null) {
            existingEntity.setName(contactList.getName());
            existingEntity.setAddress(contactList.getAddress());
            existingEntity.setPh(contactList.getPh());

            entityManager.merge(existingEntity);
            return "Successfully Updated!";
        } else {
            return "Entity with the given ID not found!";
        }
    }

    // 4:Delete
    @Transactional
    public String delete(Integer id) {
        ContactList contact = entityManager.find(ContactList.class, id);

        if (contact != null) {
            entityManager.remove(contact);
            return "Removed!";
        } else {
            return "Entity with the given id not found!";
        }
    }



    // Database Procedure Calls
    // 1: No-Args
    @Transactional
    @Modifying
    public String executeProcedure1() {
        Query query = entityManager.createNativeQuery("call INSERT_CURRENT_TIME()");
        query.executeUpdate();
        return "Executed Successfully!";
    }

    // 1: With-Args
    @Transactional
    @Modifying
    public String executeProcedure2(int id, String name) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("insertuser");

        // Set parameters
        storedProcedure.registerStoredProcedureParameter("param1", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("param2", String.class, ParameterMode.IN);

        storedProcedure.setParameter("param1", id);
        storedProcedure.setParameter("param2", name);

        // Execute the stored procedure
        storedProcedure.execute();

        return "Executed Successfully!";
    }
}
