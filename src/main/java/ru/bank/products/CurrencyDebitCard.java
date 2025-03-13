package ru.bank.products;

public class CurrencyDebitCard extends DebitCard {
    public CurrencyDebitCard(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

}