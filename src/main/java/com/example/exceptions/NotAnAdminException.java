package com.example.exceptions;

public class NotAnAdminException extends Exception {
    public NotAnAdminException() {
        super("You are not an admin! Please provide the right code!");
    }
}

