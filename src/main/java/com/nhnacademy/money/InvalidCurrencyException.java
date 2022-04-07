package com.nhnacademy.money;

public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
