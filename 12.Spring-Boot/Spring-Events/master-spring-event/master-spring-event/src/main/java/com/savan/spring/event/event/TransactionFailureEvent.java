package com.savan.spring.event.event;

public class TransactionFailureEvent {

    private String name;
    private double amount;

    public TransactionFailureEvent(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
