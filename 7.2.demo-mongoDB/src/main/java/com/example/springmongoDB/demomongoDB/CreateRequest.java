package com.example.springmongoDB.demomongoDB;

public class CreateRequest {

    private String name;
    private String authorName;
    private int cost;

    // we can also create keys starting with unserscore(_)
    private int _count;

     
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

    public void set_count(int _count) {
        this._count = _count;
    }

    public int get_count() {
        return _count;
    }
}
