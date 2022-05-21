package com.example.exceptions;

public class NumbersNotWordsException extends Exception {

    public NumbersNotWordsException() {
        super("Please enter only numbers that have maximum 2 digits!");
    }

}
