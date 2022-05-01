package com.example.exceptions;

public class PasswordNotStrongEnoughException extends Exception{
    public PasswordNotStrongEnoughException(){
        super("Password not strong enough! It should contain at least a special character, a capital letter and a digit");
    }
}
