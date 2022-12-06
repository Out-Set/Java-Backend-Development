package com.multithreading.multithreading;

public class Animal {
    private String name;
    private String type;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTyepe(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public Animal(String name, String type){
        this.name = name;
        this.type = type;
    }
}
