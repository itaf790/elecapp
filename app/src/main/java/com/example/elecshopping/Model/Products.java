package com.example.elecshopping.Model;

public class Products {

    private String pname, pdescription,pprice, pimage,pcategory, pdate,pid,ptime , ppaymentmethod , pdelivtime, pdelivfee, pbrand , pqnt;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPcategory() {
        return pcategory;
    }

    public void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPpaymentmethod() {
        return ppaymentmethod;
    }

    public void setPpaymentmethod(String ppaymentmethod) {
        this.ppaymentmethod = ppaymentmethod;
    }

    public String getPdelivtime() {
        return pdelivtime;
    }

    public void setPdelivtime(String pdelivtime) {
        this.pdelivtime = pdelivtime;
    }

    public String getPdelivfee() {
        return pdelivfee;
    }

    public void setPdelivfee(String pdelivfee) {
        this.pdelivfee = pdelivfee;
    }

    public String getPbrand() {
        return pbrand;
    }

    public void setPbrand(String pbrand) {
        this.pbrand = pbrand;
    }

    public String getPqnt() {
        return pqnt;
    }

    public void setPqnt(String pqnt) {
        this.pqnt = pqnt;
    }

    public Products(String pname, String pdescription, String pprice, String pimage, String pcategory, String pdate, String pid, String ptime, String ppaymentmethod, String pdelivtime, String pdelivfee, String pbrand, String pqnt) {
        this.pname = pname;
        this.pdescription = pdescription;
        this.pprice = pprice;
        this.pimage = pimage;
        this.pcategory = pcategory;
        this.pdate = pdate;
        this.pid = pid;
        this.ptime = ptime;
        this.ppaymentmethod = ppaymentmethod;
        this.pdelivtime = pdelivtime;
        this.pdelivfee = pdelivfee;
        this.pbrand = pbrand;
        this.pqnt = pqnt;
    }

    public Products() {


    }}



