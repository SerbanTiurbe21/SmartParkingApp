package com.example.model;

public class Parking {
    String sector;
    int number;
    boolean isOccupied;

    public Parking(String sector, int number, boolean isOccupied) {
        this.sector = sector;
        this.number = number;
        this.isOccupied = isOccupied;
    }

    public Parking() {
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
