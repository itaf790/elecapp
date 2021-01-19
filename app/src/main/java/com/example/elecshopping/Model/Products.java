package com.example.elecshopping.Model;

public class Products {

    private String pname, description,price, image,category, date,pid,time , delivery_time , delivery_fee, payment_method , pquantity , brand;

    public Products(){

    }

    public Products(String pname, String description, String price, String image, String category, String date, String pid, String time, String delivery_time, String delivery_fee, String payment_method, String pquantity, String brand) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.date = date;
        this.pid = pid;
        this.time = time;
        this.delivery_time = delivery_time;
        this.delivery_fee = delivery_fee;
        this.payment_method = payment_method;
        this.pquantity = pquantity;
        this.brand = brand;
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

    public void setDescription(String description) {
        this.description = description;
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

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}


