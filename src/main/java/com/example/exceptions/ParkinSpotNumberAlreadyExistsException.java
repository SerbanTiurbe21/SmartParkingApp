package com.example.exceptions;

public class ParkinSpotNumberAlreadyExistsException extends Exception {
    public ParkinSpotNumberAlreadyExistsException() {
        super("Parking spot already exists!");
    }
}
