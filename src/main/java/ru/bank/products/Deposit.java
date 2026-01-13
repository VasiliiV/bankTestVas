package ru.bank.products;

import java.math.BigDecimal;

public class Deposit extends BankProduct {
    private boolean isClosed = false;

    public Deposit(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(BigDecimal amount) {
        if (!isClosed) {
            balance = balance.add(amount);
        } else {
            throw new IllegalStateException("Вклад закрыт");
        }
    }

    public void close() {
        isClosed = true;
        balance = BigDecimal.ZERO;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
