package com.example.exceptions;

public class InvalidValueException extends Exception{
    public InvalidValueException() {
        super("Value cannot be negative!");
    }
}
