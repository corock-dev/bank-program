package com.nhnacademy.money;

public class NegativeMoneyWonException extends IllegalArgumentException {
    public NegativeMoneyWonException(String message) {
        super(message);
    }
}
