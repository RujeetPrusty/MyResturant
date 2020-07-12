package com.example.rujeet.myresturant.Models;

import java.util.List;

public class Request {
    private String phone;
//    private String name;
    private String address;
    private String total;
    private List<Order> foodorders;

    public Request() {
    }

    public Request(String phone,  String address, String total, List<Order> foodorders) {
        this.phone = phone;
//        this.name = name;
        this.address = address;
        this.total = total;
        this.foodorders = foodorders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoodorders() {
        return foodorders;
    }

    public void setFoodorders(List<Order> foodorders) {
        this.foodorders = foodorders;
    }
}
