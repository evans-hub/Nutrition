package com.example.nutrition.Entity;

public class User {
    private String number, name, phone,email, uuid,height,weight,gender,diseases,age;

    public User(String number, String name, String phone, String email, String uuid, String height, String weight, String gender, String diseases, String age) {
        this.number = number;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.uuid = uuid;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.diseases = diseases;
        this.age = age;
    }

    public User() {
    }

    public User(String mEmail, String mSubject, String mMessage, String uuid, String mName) {
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
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
