package com.nhnacademy.money;

public class Money {
    private long amount;
    private Currency currency;


    public Money(long money, Currency currency) {
        this.amount = money;
        this.currency = currency;
    }

    public long getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void add(Money money) {
        this.amount += money.getAmount();
    }
}
