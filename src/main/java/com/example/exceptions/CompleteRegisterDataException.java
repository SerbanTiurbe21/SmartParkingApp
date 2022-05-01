package com.example.exceptions;

public class CompleteRegisterDataException extends Exception{
    public CompleteRegisterDataException() {
        super("Please complete all the credentials!");
    }
}
