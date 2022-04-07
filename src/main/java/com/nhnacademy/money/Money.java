package com.nhnacademy.money;

public class Money {
    private long amount;
    private Currency currency;

    public long getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setAmount(long amount) {
        if(amount < 0){
            throw new NegativeMoneyWonException("Negative Money");
        }
        this.amount = amount;
    }

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.amount != ((Money)obj).getAmount()) {
            return false;
        }
        if (this.currency != ((Money)obj).getCurrency()) {
            return false;
        }
        return true;
    }
}
