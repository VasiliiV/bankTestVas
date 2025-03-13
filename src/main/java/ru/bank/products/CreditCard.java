package ru.bank.products;

public class CreditCard extends DebitCard {
    private BigDecimal interestRate;

    public CreditCard(String currency, BigDecimal balance, String name, BigDecimal interestRate) {
        super(currency, balance, name);
        this.interestRate = interestRate;
    }

    public BigDecimal getDebt() {
        return balance < 0 ? -balance : 0;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        balance -= amount; // можно уйти в минус
    }
}