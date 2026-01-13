package ru.bank.products;

import java.math.BigDecimal;

public class CurrencyDebitCard extends DebitCard {
    public CurrencyDebitCard(String currency, BigDecimal balance, String name) {
        super(currency, balance, name);
    }

}
