package com.nhnacademy.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyServiceTest {

    @BeforeEach
    void setUp() {

    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkAccountMoney() {
        Money moneyOne = new Money(2_000L, Currency.WON);
        Money moneyTwo = new Money(2_000L, Currency.WON);

        assertThat(moneyOne)
            .isEqualTo(moneyTwo);
    }

    @DisplayName("돈은 음수일 수 없다.")
    @Test
    void won_notNegative() {
        Money money = new Money(1_000L, Currency.WON);
        assertThatThrownBy(() ->  money.setAmount(-1_000L))
            .isInstanceOf(NegativeMoneyWonException.class)
            .hasMessageContaining("Negative Money");
        //FIXME
    }
}
