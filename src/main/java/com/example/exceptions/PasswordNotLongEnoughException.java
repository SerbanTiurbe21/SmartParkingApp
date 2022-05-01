package com.example.exceptions;

public class PasswordNotLongEnoughException extends Exception{
    public PasswordNotLongEnoughException(){
        super("Password not long enough!");
    }
}
