package com.savan.multitransactions.service;

import com.savan.multitransactions.db1.Inventory;
import com.savan.multitransactions.db2.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EMService {

    @PersistenceContext(unitName = "db1PersistenceUnit")
    private EntityManager db1EntityManager;

    @PersistenceContext(unitName = "db2PersistenceUnit")
    private EntityManager db2EntityManager;

    @Transactional
    public String executeNativeSqlQueries() {
        String resp = "";

        // First query for db1
        String db1Qry = """
            insert into inventory (balance, product_id) values (123, 'PID010')
            """;
        Query q1 = db1EntityManager.createNativeQuery(db1Qry);
        resp += q1.executeUpdate();

        // Second query for db2
        String db2Qry = """
            insert into orders (amount, product_id, order_id) values (345, 'PID03', 'OID00')
            """;
        Query q2 = db2EntityManager.createNativeQuery(db2Qry);
        resp += q2.executeUpdate();

        return resp;
    }

    @Transactional
    public String updateRowsThroughNq(){
        String resp = "";

        // First query for db1
        String db1Qry = """
            update inventory set balance = 999
            """;
        Query q1 = db1EntityManager.createNativeQuery(db1Qry);
        resp += q1.executeUpdate();

        // Second query for db2
        String db2Qry = """
            update orders set amount = 999
            """;
        Query q2 = db2EntityManager.createNativeQuery(db2Qry);
        resp += q2.executeUpdate();

        return resp;
    }

    public String readFromEm(){
        String resp = "";

        // First query for db1
        String db1Qry = """
            select * from  inventory
            """;
        Query q1 = db1EntityManager.createNativeQuery(db1Qry, Inventory.class);
        resp += q1.getResultList();

        // Second query for db2
        String db2Qry = """
            select * from  orders
            """;
        Query q2 = db2EntityManager.createNativeQuery(db2Qry, Order.class);
        resp += q2.getResultList();

        return resp;
    }

}
