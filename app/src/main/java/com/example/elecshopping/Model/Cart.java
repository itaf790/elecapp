package com.example.elecshopping.Model;

public class Cart {

    private String pid,pname,price,quantity,discount, brand , date , time , deliveryfee , deliverrytime , paymentmethod  ;

    public Cart(String pid, String pname, String price, String quantity, String discount, String brand, String date, String time, String deliveryfee, String deliverrytime, String paymentmethod) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.brand = brand;
        this.date = date;
        this.time = time;
        this.deliveryfee = deliveryfee;
        this.deliverrytime = deliverrytime;
        this.paymentmethod = paymentmethod;
    }

    public Cart() {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(String deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public String getDeliverrytime() {
        return deliverrytime;
    }

    public void setDeliverrytime(String deliverrytime) {
        this.deliverrytime = deliverrytime;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }
}
