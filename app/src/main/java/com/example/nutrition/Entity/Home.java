package com.example.nutrition.Entity;

public class Home {
    private String title,file,description;

    public Home(String title, String file,String description) {
        this.title = title;
        this.file = file;
        this.description=description;
    }

    public Home() {
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
