package com.example.exceptions;

public class InsertNumberException extends Exception{
    public InsertNumberException() {
        super("Please insert a valid number!");
    }
}
