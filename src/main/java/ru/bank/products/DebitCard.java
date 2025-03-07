package ru.bank.products;

public class DebitCard extends BankProduct {

    public DebitCard(String currency, double balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }
}