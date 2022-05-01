package com.example.exceptions;

public class CredentialsAreNotEmptyException extends Exception{
    public CredentialsAreNotEmptyException() {
        super("Your credentials are empty!");
    }
}
