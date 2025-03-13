package ru.bank.products;

public class DebitCard extends BankProduct {

    public DebitCard(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(BigDecimal amount) {
        balance += amount;
    }

    public void withdraw(BigDecimal amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }
}