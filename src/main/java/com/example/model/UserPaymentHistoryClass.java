package com.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UserPaymentHistoryClass {
    private int id;
    private String username;
    private String payment;
    private String date;

    public UserPaymentHistoryClass() {
    }

    public UserPaymentHistoryClass(int id, String username, String payment, String date) {
        this.id = id;
        this.username = username;
        this.payment = payment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPayment() {
        return payment;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        String[] split = payment.split("\\s+");
        String rez = " ";
        for(int i=0;i<split.length;i++){
            rez += split[i] + "\n";
        }
        return rez;
    }
}