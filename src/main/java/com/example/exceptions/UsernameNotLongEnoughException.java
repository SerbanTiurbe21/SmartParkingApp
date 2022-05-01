package com.example.exceptions;

public class UsernameNotLongEnoughException extends Exception{
    public UsernameNotLongEnoughException(){
        super("Username is not long enough!");
    }
}
