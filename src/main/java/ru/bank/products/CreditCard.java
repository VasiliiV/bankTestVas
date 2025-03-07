package ru.bank.products;

public class CreditCard extends DebitCard {
    private double interestRate;

    public CreditCard(String currency, double balance, String name, double interestRate) {
        super(currency, balance, name);
        this.interestRate = interestRate;
    }

    public double getDebt() {
        return balance < 0 ? -balance : 0;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount; // можно уйти в минус
    }
}