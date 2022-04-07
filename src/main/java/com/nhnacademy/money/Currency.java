package com.nhnacademy.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Currency implements Exchangable {
    WON() {
        @Override
        public Money exchange(Money beforeExchangeMoney, Currency currency) {
            if (currency == DOLLAR) {
                return new Money(beforeExchangeMoney.getAmount().divide(BigDecimal.valueOf(1_000), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR), DOLLAR);
            }
            return new Money(beforeExchangeMoney.getAmount().multiply(BigDecimal.valueOf(10)).setScale(0, RoundingMode.FLOOR), MESO);
        }
    },
    DOLLAR() {
        @Override
        public Money exchange(Money beforeExchangeMoney, Currency currency) {
            if (currency == WON) {
                return new Money(beforeExchangeMoney.getAmount().multiply(BigDecimal.valueOf(1_000)).setScale(0, RoundingMode.FLOOR), WON);
            }
            return new Money(beforeExchangeMoney.getAmount().multiply(BigDecimal.valueOf(10_000)).setScale(0, RoundingMode.FLOOR), MESO);
        }
    },
    MESO() {
        @Override
        public Money exchange(Money beforeExchangeMoney, Currency currency) {
            if (currency == WON) {
                return new Money(beforeExchangeMoney.getAmount().divide(BigDecimal.valueOf(10), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR), WON);
            }
            return new Money(beforeExchangeMoney.getAmount().divide(BigDecimal.valueOf(10_000), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR), DOLLAR);
        }
    };
}
