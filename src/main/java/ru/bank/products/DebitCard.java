package ru.bank.products;

import java.math.BigDecimal;

public class DebitCard extends BankProduct {

    public DebitCard(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }
}
