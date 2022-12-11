package com.example.jpabeans.demojpabeans;

public class HelloWorld {
    private String message;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HelloWorld(String message, String data) {
        this.message = message;
        this.data = data;
    }    

    public HelloWorld(){

    }

    @Override
    public String toString() {
        return "message = " + this.message + ", data = " + this.data;
    }
}
