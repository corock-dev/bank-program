package com.nhnacademy.money;

import java.math.BigDecimal;
import java.util.Objects;

import static com.nhnacademy.money.Currency.*;

public class Money implements Exchangable {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        BigDecimal zero = BigDecimal.valueOf(0);

        if (amount.compareTo(zero) < 0) {
            throw new IllegalArgumentException("Invaild amount: " + amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(BigDecimal amount) {
        return new Money(amount, DOLLAR);
    }

    public static Money won(BigDecimal amount) {
        return new Money(amount, WON);

    }

    public static Money meso(BigDecimal amount) {
        return new Money(amount, MESO);
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public BigDecimal getAmount() {
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
        return Objects.equals(amount, money.amount) && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        if (this.amount.compareTo(other.amount) < 0) {
            throw new IllegalArgumentException("other money greater then this ");
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    private void checkCurrency(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new InvalidCurrencyException(
                    "Not matched currency. this currency : " + this.currency + System.lineSeparator() +
                            "other currency " + other.currency);
        }
    }

    @Override
    public Money exchange(Money beforeExchangeMoney, Currency to) {
        return currency.exchange(beforeExchangeMoney, to);
    }
}
