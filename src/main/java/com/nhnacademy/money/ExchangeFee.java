package com.nhnacademy.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.nhnacademy.money.Currency.DOLLAR;
import static com.nhnacademy.money.Currency.WON;

public class ExchangeFee {
    public Money exchangeToWon(Money dollar) {
        return new Money(dollar.getAmount().multiply(BigDecimal.valueOf(1_000)).setScale(0, RoundingMode.FLOOR), WON);
    }

    public Money exchangeToDollar(Money won) {
        return new Money(won.getAmount().divide(BigDecimal.valueOf(1_000), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR), DOLLAR);
    }
}
