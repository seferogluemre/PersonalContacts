package com.emreseferoglu.personalcontacts;

public class Person {
    private int id;
    private String name;
    private String phone;

    public Person(String name, String phone, int id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}