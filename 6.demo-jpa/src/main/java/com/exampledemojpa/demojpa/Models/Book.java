package com.exampledemojpa.demojpa.Models;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// @Table(name = "my_book")  // Changing the table name
public class Book {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generation of unique Ids
    private int id;

    // @Column(name = "book_name")   // Changing the column name
    private String name;

    private String authorName;

    // @Column(name = "price")    // Changing the column name
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Book(int id, String name, String authorName, int cost) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.cost = cost;
    }


    // You must have a default constructor, if you have defined a parameterized constructor.
    public Book() {

    }


    // Whenever you wanto to print the data, you need to override the toString() method.
    @Override
    public String toString(){
        return "{ id = " + this.id + ", name = " + this.getName() + ", authoyName = " + this.getAuthorName() + ", cost = " + this.getCost() + " }";
    }


    // For Id auto generated, we nooed not to pass id 
    public Book(String name, String authorName, int cost) {
        this.name = name;
        this.authorName = authorName;
        this.cost = cost;
    }
    
}
