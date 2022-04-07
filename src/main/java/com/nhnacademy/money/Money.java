package com.nhnacademy.money;

import static com.nhnacademy.money.Currency.DOLLAR;
import static com.nhnacademy.money.Currency.WON;

import java.util.Objects;

public class Money {
    private long amount;
    private Currency currency;

    public Money(long amount, Currency currency) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invaild amount: " + amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(long amount) {
       return new Money(amount, DOLLAR);
    }

    public static Money won(long amount) {
        return new Money(amount, WON);

    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount + other.amount, this.currency);
    }

    public long getAmount() {
        return this.amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        if (this.amount < other.amount) {
            throw new IllegalArgumentException("other money greater then this ");
        }
        return new Money(this.amount - other.amount, this.currency);
    }

    private void checkCurrency(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new InvalidCurrencyException(
                "Not matched currency. this currency : " + this.currency + System.lineSeparator() +
                    "other currency " + other.currency);
        }
    }

}
