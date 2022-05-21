package com.example.exceptions;

public class InsufficientFoundsException extends Exception{
    public InsufficientFoundsException() {
        super("Insufficient founds!\nPlease go back to deposit!");
    }
}
