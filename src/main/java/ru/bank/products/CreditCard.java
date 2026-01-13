package ru.bank.products;

import java.math.BigDecimal;

public class CreditCard extends DebitCard {
    private BigDecimal interestRate;

    public CreditCard(String currency, BigDecimal balance, String name, BigDecimal interestRate) {
        super(currency, balance, name);
        this.interestRate = interestRate;
    }

    public BigDecimal getDebt() {
        return balance.compareTo(BigDecimal.ZERO) < 0 ? balance.negate() : BigDecimal.ZERO;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount); // можно уйти в минус
    }
}
