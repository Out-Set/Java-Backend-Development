package com.savan.reactive.programming.parallelstreams;

public class Student {

    private Integer id;
    private String name;
    private Long mobileNumber;
    private Integer age;

    public Student(Integer id, String name, Long mobileNumber, Integer age) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }
}
