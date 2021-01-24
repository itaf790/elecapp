package com.example.elecshopping.Model;

public class Policies {


    public Policies(String delivery_fee, String delivery_time, String exchange_policy, String returns_policy, String payment_methods) {
        Delivery_fee = delivery_fee;
        Delivery_time = delivery_time;
        Exchange_policy = exchange_policy;
        Returns_policy = returns_policy;
        Payment_methods = payment_methods;
    }

    private String Delivery_fee;
    private String Delivery_time;
    private String Exchange_policy;
    private String Returns_policy;
    private String Payment_methods;



    public Policies(){}


    public String getDelivery_fee() {
        return Delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        Delivery_fee = delivery_fee;
    }

    public String getDelivery_time() {
        return Delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        Delivery_time = delivery_time;
    }

    public String getExchange_policy() {
        return Exchange_policy;
    }

    public void setExchange_policy(String exchange_policy) {
        Exchange_policy = exchange_policy;
    }

    public String getReturns_policy() {
        return Returns_policy;
    }

    public void setReturns_policy(String returns_policy) {
        Returns_policy = returns_policy;
    }

    public String getPayment_methods() {
        return Payment_methods;
    }

    public void setPayment_methods(String payment_methods) {
        Payment_methods = payment_methods;
    }
}
