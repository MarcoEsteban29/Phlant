package com.example.simonh07.phlant;

public class Model {
    String title;
    String image;
    String description;
    String url;


    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }




    public Model() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
