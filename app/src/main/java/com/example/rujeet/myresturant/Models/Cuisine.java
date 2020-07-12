package com.example.rujeet.myresturant.Models;

public class Cuisine {

    public String image;
    public String cuisinename;

    public Cuisine() {
    }

    public Cuisine(String image, String cuisinename) {
        this.image = image;
        this.cuisinename = cuisinename;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCuisinename() {
        return cuisinename;
    }

    public void setCuisinename(String cuisinename) {
        this.cuisinename = cuisinename;
    }
}
