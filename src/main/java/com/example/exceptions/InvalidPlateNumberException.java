package com.example.exceptions;

public class InvalidPlateNumberException extends Exception{
    public InvalidPlateNumberException() {
        super("Plate number should have the next form: XX 00 AAA, where XX can contain letters, 00 can be anynumber from 1 to 99 and AAA can contain any letters!");
    }
}
