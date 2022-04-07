package com.nhnacademy.money;

import static com.nhnacademy.money.Currency.DOLLAR;
import static com.nhnacademy.money.Currency.WON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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
        Money m1 = Money.won(BigDecimal.valueOf(1_000));
        Money m2 = Money.won(BigDecimal.valueOf(1_000));

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(BigDecimal.valueOf(2_000));
        assertThat(sum.getCurrency()).isEqualTo(WON);
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void checkAccountMoney() {
        Money m1 = new Money(BigDecimal.valueOf(2000), DOLLAR);
        Money m2 = new Money(BigDecimal.valueOf(2000), DOLLAR);

        assertThat(m1.equals(m2)).isTrue();
    }

    @DisplayName("돈은 음수일수없다")
    @Test
    void money_isNotNegative_IllgalEx() {
        BigDecimal amount = BigDecimal.valueOf(-1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Money(amount, WON))
                .withMessageContaining("Invaild", amount);
    }

    @DisplayName("5$ + 5$ = 10$")
    @Test
    void add_5DollorPlus5Dolalr() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.dollar(BigDecimal.valueOf(5));

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(sum.getCurrency()).isEqualTo(DOLLAR);
    }

    @DisplayName("돈을 더할 시 통화가 다르면 더할수없다")
    @Test
    void add_notMatchCurrency_throwex() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.won(BigDecimal.valueOf(5000));

        assertThatThrownBy(() -> m1.add((m2)))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");
    }

    @DisplayName("5 - 6 = 오류")
    @Test
    void subtrack_negative_throwEx() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.dollar(BigDecimal.valueOf(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> m1.subtract(m2))
                .withMessageContaining("greater");
    }

    @DisplayName("5 - 4 = 1")
    @Test
    void subtract() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.dollar(BigDecimal.valueOf(4));

        Money result = m1.subtract(m2);

        assertThat(result).isEqualTo(Money.dollar(BigDecimal.valueOf(1)));
    }

    @DisplayName("돈을 뺄 시 통화가 다르면 더할수없다")
    @Test
    void subtract_notMatchCurrency_throwEx() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.won(BigDecimal.valueOf(5000));

        assertThatThrownBy(() -> m1.subtract((m2)))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");
    }

    @DisplayName("5.25$ + 5.25$ = 10.50$ (소숫점 이하 2자리)")
    @Test
    void add_decimalPoint() {
        Money m1 = Money.dollar(BigDecimal.valueOf(525, 2));
        Money m2 = Money.dollar(BigDecimal.valueOf(525, 2));

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(BigDecimal.valueOf(1050, 2));
        assertThat(sum.getCurrency()).isEqualTo(DOLLAR);
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

    @DisplayName("1,000원 - 환전-> 1$")
    @Test
    void wonToDollar() {
        ExchangeFee exchangeFee = new ExchangeFee();
        Money dollarMoney = exchangeFee.exchangeToDollar(Money.dollar(BigDecimal.valueOf(1_000)));
        assertThat(dollarMoney.getAmount().compareTo(BigDecimal.valueOf(1)) == 0).isSameAs(true);
        assertThat(dollarMoney.getCurrency())
                .isEqualTo(Currency.valueOf("DOLLAR"));
    }

    @DisplayName("5.25$ -> 5,250원")
    @Test
    void dollarToWon() {
        ExchangeFee exchangeFee = new ExchangeFee();
        Money wonMoney = exchangeFee.exchangeToWon(Money.dollar(BigDecimal.valueOf(5.25)));
        assertThat(wonMoney.getAmount())
                .isEqualTo(BigDecimal.valueOf(5250));
        assertThat(wonMoney.getCurrency())
                .isEqualTo(Currency.valueOf("WON"));
    }

    @DisplayName("달러 -> 원화: 5원 이상 -> 10원으로 반올림")
    @Test
    void dollarToWon_roundOff() {
        ExchangeFee exchangeFee = new ExchangeFee();
        Money dollarMoney = Money.dollar(BigDecimal.valueOf(5.255));

        assertThat(exchangeFee.exchangeToWon(dollarMoney).getAmount())
            .isEqualTo(BigDecimal.valueOf(5260));
    }

    @DisplayName("원화 -> 달러: $0.005 이상 -> $0.01 반올림")
    @Test
    void wonToDollar_roundOff() {
        ExchangeFee exchangeFee = new ExchangeFee();
        Money wonMoney = Money.dollar(BigDecimal.valueOf(5255));

        assertThat(exchangeFee.exchangeToDollar(wonMoney).getAmount())
            .isEqualTo(BigDecimal.valueOf(5.26));
    }
}
