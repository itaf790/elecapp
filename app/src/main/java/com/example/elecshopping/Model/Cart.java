package com.example.elecshopping.Model;

public class Cart {

    private String pid,pname,price,pquantity, brand , date , time , delivery_fee , delivery_time , payment_method , numberquantity , totalAmount , overdiscount ,discount, image ;

    public Cart(String pid, String pname,String totalAmount, String price,String image, String pquantity,  String brand, String date, String time, String delivery_fee, String delivery_time, String payment_method, String numberquantity) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.pquantity = pquantity;
        this.brand = brand;
        this.date = date;
        this.time = time;
        this.image = image;
        this.delivery_fee = delivery_fee;
        this.delivery_time = delivery_time;
        this.payment_method = payment_method;
        this.numberquantity = numberquantity;
        this.totalAmount = totalAmount;
    }

    public Cart(String overdiscount) {
        this.overdiscount = overdiscount;
    }

    public String getOverdiscount() {
        return overdiscount;
    }

    public void setOverdiscount(String overdiscount) {
        this.overdiscount = overdiscount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Cart() {
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getNumberquantity() {
        return numberquantity;
    }

    public void setNumberquantity(String numberquantity) {
        this.numberquantity = numberquantity;
    }
}
