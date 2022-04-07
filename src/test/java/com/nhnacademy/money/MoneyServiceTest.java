package com.nhnacademy.money;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyServiceTest {

    @BeforeEach
    void setUp() {

    }

    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void add_sameUnitOfMoney() {
        Money money = new Money(1000L, Currency.WON);
        money.add(money);

        assertEquals(money.getAmount(), 2000L);
    }
}
