package com.nhnacademy.money;

import java.math.BigDecimal;

public class Bank {
    public Money subtractMileage(Money mesoMoney, int month) {
        if (mesoMoney.getAmount().compareTo(BigDecimal.valueOf(10_000)) < 0) {
            return mesoMoney;
        }
        return new Money(mesoMoney.getAmount().subtract(BigDecimal.valueOf(month * 1_000L)), mesoMoney.getCurrency());
    }
}
