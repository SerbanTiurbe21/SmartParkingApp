package com.example.model;

public class User {
    private String username;
    private String password;
    private String role;
    private int balance;
    private boolean parkA;
    private boolean parkB;
    private boolean parkC;

    public User(String username, String password, String role, int balance, boolean parkA, boolean parkB, boolean parkC) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.parkA = parkA;
        this.parkB = parkB;
        this.parkC = parkC;
    }

    public User(){}

    public int getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isParkA() {
        return parkA;
    }

    public void setParkA(boolean parkA) {
        this.parkA = parkA;
    }

    public boolean isParkB() {
        return parkB;
    }

    public void setParkB(boolean parkB) {
        this.parkB = parkB;
    }

    public boolean isParkC() {
        return parkC;
    }

    public void setParkC(boolean parkC) {
        this.parkC = parkC;
    }
}
