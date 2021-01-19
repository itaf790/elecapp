package com.example.elecshopping.Model;

public class Users {

    private String Email,Name,Password,Phone_number, Address, Last_name;

    public Users(){

    }

    public Users(String email, String name, String password, String phone_number, String address, String last_name) {
        Email = email;
        Name = name;
        Password = password;
        Phone_number = phone_number;
        Address = address;
        Last_name = last_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }
}
