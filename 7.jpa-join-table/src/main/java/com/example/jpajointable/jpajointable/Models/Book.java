package com.example.jpajointable.jpajointable.Models;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
public class Book {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String authorName;

    private int cost;

    @ManyToOne
    @JoinColumn
    private BookCategory bookCategory;


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

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }


    // For Id auto generated, we need not to pass id 
    public Book(String name, String authorName, int cost) {
        this.name = name;
        this.authorName = authorName;
        this.cost = cost;
    }

    // Since we have already our bookCategory, so we will take the Id(int type) only, not the bookCategory object.
    public Book(String name, String authorName, int cost, int bookCategory) {
        this.name = name;
        this.authorName = authorName;
        this.cost = cost;

        this.bookCategory = new BookCategory();

        // Since we have already our bookCategory, we just need to get the Id of bookCategory.
        this.bookCategory.setId(bookCategory);
    }

    // You must have a default constructor, if you have defined a parameterized constructor.
    public Book() {

    }


    // Whenever you wanto to print the data, you need to override the toString() method.
    @Override
    public String toString(){
        return "{ id = " + this.id + ", name = " + this.getName() + ", authorName = " + this.getAuthorName() + ", cost = " + this.getCost() + " }";
    }
     
}
