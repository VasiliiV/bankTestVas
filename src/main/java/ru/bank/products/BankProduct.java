package ru.bank.products;

public abstract class BankProduct {
    protected String currency;
    protected double balance;
    protected String name;

    public BankProduct(String currency, double balance, String name) {
        this.currency = currency;
        this.balance = balance;
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }
}