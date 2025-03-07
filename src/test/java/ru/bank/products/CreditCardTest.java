package ru.bank.products;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CreditCardTest {

    @Test
    public void testCreditCardDebt() {
        CreditCard card = new CreditCard("RUB", 0, "Кредитная карта", 15);
        card.withdraw(500);
        Assert.assertEquals(card.getDebt(), 500);
    }

    @Test
    public void testInterestRate() {
        CreditCard card = new CreditCard("RUB", 0, "Кредитная карта", 15);
        Assert.assertEquals(card.getInterestRate(), 15.0);
    }
}