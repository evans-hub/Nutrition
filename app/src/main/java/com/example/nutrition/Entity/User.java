package com.example.nutrition.Entity;

public class User {
    private String number, name, phone,email, uuid;

    public User(String number, String name, String phone, String email, String uuid) {
        this.number = number;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.uuid = uuid;
    }

    public User() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
