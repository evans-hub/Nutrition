package com.example.nutrition.Entity;

public class Home {
    private String title,file,description,calories,fat,protein,carbo;

    public Home(String title, String file,String description) {
        this.title = title;
        this.file = file;
        this.description=description;
    }

    public Home(String title, String file, String description, String calories, String fat, String protein, String carbo) {
        this.title = title;
        this.file = file;
        this.description = description;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbo = carbo;
    }

    public Home() {
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
