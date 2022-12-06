package com.example.demo;

import java.util.HashMap;

public class DataStore {
    
    private HashMap<String, String> store = new HashMap<String, String>();
    
    public DataStore() {
        store.put("Sachin", "A Great Player");
        store.put("Ramu", "A Great Man");
        store.put("GfG", "Great Website");
    }

    public String getFromDB(String word){
        return store.get(word);
    }

}
