package com.example.exceptions;

public class CompleteLoginDataException extends Exception{
    public CompleteLoginDataException() {
        super("Login data is not correct!\nPlease re-enter your correct credentials!");
    }
}
