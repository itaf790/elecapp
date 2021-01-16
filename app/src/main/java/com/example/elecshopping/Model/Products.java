package com.example.elecshopping.Model;

public class Products {

    private String pname, description,price, image,category, date,pid,time , paymentmethod , delivtime, delivfee, brand , qnt;

    public Products(String paymentmethod, String delivtime, String delivfee, String brand, String qnt) {
        this.paymentmethod = paymentmethod;
        this.delivtime = delivtime;
        this.delivfee = delivfee;
        this.brand = brand;
        this.qnt = qnt;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getDelivtime() {
        return delivtime;
    }

    public void setDelivtime(String delivtime) {
        this.delivtime = delivtime;
    }

    public String getDelivfee() {
        return delivfee;
    }

    public void setDelivfee(String delivfee) {
        this.delivfee = delivfee;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public Products(){


    }

    public Products(String pname, String description, String price, String image, String category, String date, String pid, String time) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.date = date;
        this.pid = pid;
        this.time = time;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
