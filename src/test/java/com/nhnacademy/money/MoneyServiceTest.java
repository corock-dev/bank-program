package com.nhnacademy.money;

import static com.nhnacademy.money.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
        Money moneyOne = Money.won(BigDecimal.valueOf(1_000));
        Money moneyTwo = Money.won(BigDecimal.valueOf(1_000));

        Money result = moneyOne.add(moneyTwo);

        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(2_000));
        assertThat(result.getCurrency()).isEqualTo(WON);
    }

    @DisplayName("2,000원과 2,000원은 같다.(equals)")
    @Test
    void isSameMoney_ifAmountAndCurrency() {
        Money moneyOne = new Money(BigDecimal.valueOf(2000), DOLLAR);
        Money moneyTwo = new Money(BigDecimal.valueOf(2000), DOLLAR);

        assertThat(Objects.equals(moneyOne, moneyTwo)).isTrue();
    }

    @DisplayName("돈은 음수일 수 없다")
    @Test
    void money_isNotNegative_illegalArgumentException() {
        BigDecimal amount = BigDecimal.valueOf(-1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Money(amount, WON))
                .withMessageContaining("Invaild", amount);
    }

    @DisplayName("5$ + 5$ = 10$")
    @Test
    void add_dollars() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(5));
        Money moneyTwo = Money.dollar(BigDecimal.valueOf(5));

        Money result = moneyOne.add(moneyTwo);

        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(result.getCurrency()).isEqualTo(DOLLAR);
    }

    @DisplayName("돈을 더할 시 통화가 다르면 더할수없다")
    @Test
    void add_ifNotMatchedCurrency_throwInvalidCurrencyException() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(5));
        Money moneyTwo = Money.won(BigDecimal.valueOf(5000));

        assertThatThrownBy(() -> moneyOne.add((moneyTwo)))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");
    }

    @DisplayName("5 - 6 = 오류")
    @Test
    void subtractMoney_isNegative_throwIllegalArgumentException() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(5));
        Money moneyTwo = Money.dollar(BigDecimal.valueOf(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> moneyOne.subtract(moneyTwo))
                .withMessageContaining("greater");
    }

    @DisplayName("5 - 4 = 1")
    @Test
    void subtractMoney_isValid() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(5));
        Money moneyTwo = Money.dollar(BigDecimal.valueOf(4));

        Money result = moneyOne.subtract(moneyTwo);

        assertThat(result).isEqualTo(Money.dollar(BigDecimal.valueOf(1)));
    }

    @DisplayName("돈을 뺄 시 통화가 다르면 더할수없다")
    @Test
    void subtractMoney_ifNotMatchedCurrency_throwInvalidCurrencyException() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(5));
        Money moneyTwo = Money.won(BigDecimal.valueOf(5000));

        assertThatThrownBy(() -> moneyOne.subtract((moneyTwo)))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");
    }

    @DisplayName("5.25$ + 5.25$ = 10.50$ (소숫점 이하 2자리)")
    @Test
    void add_Dollars_andFractionIsTwo() {
        Money moneyOne = Money.dollar(BigDecimal.valueOf(525, 2));
        Money moneyTwo = Money.dollar(BigDecimal.valueOf(525, 2));

        Money result = moneyOne.add(moneyTwo);

        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(1050, 2));
        assertThat(result.getCurrency()).isEqualTo(DOLLAR);
    }

    @DisplayName("통화는 달러화와 원화만이 존재 (후에 메소 통화가 추가되었음)")
    @Test
    void currency_onlyWonDollar() {
        List<Currency> currencies = List.of(Currency.values());
        assertThat(currencies.size())
                .isEqualTo(3);
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

    @DisplayName("다른 통화(ex: 유로화) 추가해보기 (환율은 임의대로)")
    @Test
    void add_anotherCurrency() {
        List<Currency> currencies;
        currencies = List.of(Currency.values());

        Money money = Money.dollar(BigDecimal.valueOf(5.25));
        Exchangable exchangableMoney = money;
        Money exchangedMoney = exchangableMoney.exchange(money, MESO);

        assertThat(currencies.size())
                .isEqualTo(3);
        assertThat(currencies.contains(Currency.valueOf("MESO")))
                .isTrue();
        assertThat(exchangedMoney.getAmount())
                .isEqualTo(BigDecimal.valueOf(52500));
        assertThat(exchangedMoney.getCurrency())
                .isEqualTo(Currency.valueOf("MESO"));
    }

    @DisplayName("마일리지가 10_000원 이상일 경우에는 달마다 1_000원씩 차감된다")
    @Test()
    void ifMileageGreaterThanOrEqual10000() {
        Money mesoMoney = Money.meso(BigDecimal.valueOf(10_000));
        Bank bank = new Bank();

        Money subtractedMoney = bank.subtractMileage(mesoMoney, 1);
        assertThat(subtractedMoney.getAmount()).isEqualTo(BigDecimal.valueOf(9_000));

        subtractedMoney = bank.subtractMileage(mesoMoney, 2);
        assertThat(subtractedMoney.getAmount()).isEqualTo(BigDecimal.valueOf(8_000));

        subtractedMoney = bank.subtractMileage(mesoMoney, 3);
        assertThat(subtractedMoney.getAmount()).isEqualTo(BigDecimal.valueOf(7_000));
    }

    @DisplayName("마일리지가 10_000원 미만일 경우에는 차감되지 않는다")
    @Test()
    void ifMileageLessThan10000() {
        Money mesoMoney = Money.meso(BigDecimal.valueOf(9_999));
        Bank bank = new Bank();

        Money notSubtractedMoney = bank.subtractMileage(mesoMoney, 1);
        assertThat(notSubtractedMoney.getAmount()).isEqualTo(BigDecimal.valueOf(9_999));
    }
}
