package ru.bank.products;

public class Deposit extends BankProduct {
    private boolean isClosed = false;

    public Deposit(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(BigDecimal amount) {
        if (!isClosed) {
            balance += amount;
        } else {
            throw new IllegalStateException("Вклад закрыт");
        }
    }

    public void close() {
        isClosed = true;
        balance = 0;
    }

    public boolean isClosed() {
        return isClosed;
    }
}