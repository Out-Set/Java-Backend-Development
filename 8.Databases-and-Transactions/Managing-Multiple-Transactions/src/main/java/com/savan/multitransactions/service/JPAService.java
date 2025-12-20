package com.savan.multitransactions.service;

import com.savan.multitransactions.db1.Inventory;
import com.savan.multitransactions.db1.InventoryRepo;
import com.savan.multitransactions.db2.Order;
import com.savan.multitransactions.db2.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JPAService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private OrderRepo orderRepo;

    public String saveDataInBothDatabases() {
        // Insert/Update logic for both DB1 and DB2

        // Save Inventory
        Inventory inventory1 = new Inventory("I10", 999L);
        Inventory inventory2 = new Inventory("I11", 777L);
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory1);
        inventoryList.add(inventory2);
        List<Inventory> r1 = inventoryRepo.saveAll(inventoryList);

        // Save Order
        Order order1 = new Order("O22", "I10", 999L);
        Order order2 = new Order("O55","I11", 777L);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        List<Order> r2 = orderRepo.saveAll(orderList);

        return r1+", "+r2;
    }

    public String readThroughJpa(){
        String resp = "";

        resp += inventoryRepo.findAll();
        resp += orderRepo.findAll();

        return resp;
    }
}

