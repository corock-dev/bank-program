package com.nhnacademy.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.nhnacademy.money.Currency.DOLLAR;
import static com.nhnacademy.money.Currency.WON;

public class ExchangeFee {
    public Money exchangeToWon(Money dollar) {
        Money halfUpDollarMoney = new Money(dollar.getAmount().setScale(2, RoundingMode.CEILING), DOLLAR); // 5.255 -> 5.26 으로 하는 작업
        return new Money(halfUpDollarMoney.getAmount().multiply(BigDecimal.valueOf(1_000)).setScale(0), WON);
    }

    public Money exchangeToDollar(Money won) {
        Money divideWonMoney = new Money(won.getAmount().divide(BigDecimal.valueOf(1_000)), WON); // 5255 -> 5.255
        return new Money(divideWonMoney.getAmount().setScale(2, RoundingMode.CEILING), DOLLAR);
    }
}
