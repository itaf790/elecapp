package com.example.elecshopping.Model;

public class Policies {

    private String polid, deliveryfee, deliverytime, exchangepolicy, returnspolicy, paymentmethod;

    public Policies(){}

    public Policies(String polid, String deliveryfee, String deliverytime, String exchangepolicy, String returnspolicy, String paymentmethod) {
        this.polid = polid;
        this.deliveryfee = deliveryfee;
        this.deliverytime = deliverytime;
        this.exchangepolicy = exchangepolicy;
        this.returnspolicy = returnspolicy;
        this.paymentmethod = paymentmethod;
    }


    public String getPolid() {
        return polid;
    }

    public void setPolid(String polid) {
        this.polid = polid;
    }

    public String getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(String deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getExchangepolicy() {
        return exchangepolicy;
    }

    public void setExchangepolicy(String exchangepolicy) {
        this.exchangepolicy = exchangepolicy;
    }

    public String getReturnspolicy() {
        return returnspolicy;
    }

    public void setReturnspolicy(String returnspolicy) {
        this.returnspolicy = returnspolicy;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }
}
