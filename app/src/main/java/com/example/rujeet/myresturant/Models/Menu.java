package com.example.rujeet.myresturant.Models;

public class Menu {
    public String menuname, image, menuid,menuprice;

    public Menu() {
    }



    public Menu(String menuname, String image, String menuid, String menuprice) {
        this.menuname = menuname;
        this.image = image;
        this.menuid = menuid;
        this.menuprice = menuprice;

    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }
}

