package com.nhnacademy.money;

import java.math.BigDecimal;

public interface Exchangable {
    Money exchange(Money beforeExchangeMoney, Currency currency);
}
