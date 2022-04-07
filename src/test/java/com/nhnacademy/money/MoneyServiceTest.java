package com.nhnacademy.money;

import static com.nhnacademy.money.Currency.DOLLAR;
import static com.nhnacademy.money.Currency.WON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyServiceTest {

    @BeforeEach
    void setUp() {

    }

    @DisplayName("1,000원 + 1,000원 = 2,000원")
    @Test
    void add() {
        Money m1 = Money.won(1_000L);
        Money m2 = Money.won(1_000L);

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(2_000L);
        assertThat(sum.getCurrency()).isEqualTo(WON);
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkAccountMoney() {
        Money m1 = new Money(2_000L, DOLLAR);
        Money m2 = new Money(2_000L, DOLLAR);

        assertThat(m1.equals(m2)).isTrue();
    }

    @DisplayName("돈은 음수일수없다")
    @Test
    void money_isNotNegative_IllgalEx() {
        long amount = -1L;

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(amount, WON))
            .withMessageContaining("Invaild", amount);
    }

    @DisplayName("5$ + 5$ = 10$")
    @Test
    void add_5DollorPlus5Dolalr() {
        Money m1 = Money.dollar(5);
        Money m2 = Money.dollar(5);

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(10);
        assertThat(sum.getCurrency()).isEqualTo(DOLLAR);
    }

    @DisplayName("돈을 더할 시 통화가 다르면 더할수없다")
    @Test
    void add_notMatchCurrency_throwex() {
        Money m1 = Money.dollar(5);
        Money m2 = Money.won(5_000L);

        assertThatThrownBy(() -> m1.add((m2)))
            .isInstanceOf(InvalidCurrencyException.class)
            .hasMessageContaining("currency");
    }

    @DisplayName("5 - 6 = 오류")
    @Test
    void subtrack_negative_throwEx() {
        Money m1 = Money.dollar(5);
        Money m2 = Money.dollar(6);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> m1.subtract(m2))
            .withMessageContaining("greater");
    }

    @DisplayName("5 - 4 = 1")
    @Test
    void subtrack() {
        Money m1 = Money.dollar(5);
        Money m2 = Money.dollar(4);

        Money result = m1.subtract(m2);

        assertThat(result).isEqualTo(Money.dollar(1));
    }

    @DisplayName("돈을 뺄 시 통화가 다르면 더할수없다")
    @Test
    void subtrack_notMatchCurrency_throwex() {
        Money m1 = Money.dollar(5);
        Money m2 = Money.won(5_000L);

        assertThatThrownBy(() -> m1.subtract((m2)))
            .isInstanceOf(InvalidCurrencyException.class)
            .hasMessageContaining("currency");
    }

    @DisplayName("통화는 달러화와 원화만이 존재")
    @Test
    void currency_onlyWonDollar() {
        List<Currency> currencies;
        currencies = List.of(Currency.values());
        assertThat(currencies.size())
            .isEqualTo(2);
        assertThat(currencies.contains(Currency.valueOf("WON")))
            .isTrue();
        assertThat(currencies.contains(Currency.valueOf("DOLLAR")))
            .isTrue();
    }
}
