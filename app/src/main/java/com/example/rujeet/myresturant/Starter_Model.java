package com.example.rujeet.myresturant;

public class Starter_Model {
    private int image;
    private String text;

    public Starter_Model(int image,String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }
}
