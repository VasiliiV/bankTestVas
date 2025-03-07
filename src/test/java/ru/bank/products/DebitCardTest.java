package ru.bank.products;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DebitCardTest {

    @Test
    public void testDebitCardDeposit() {
        DebitCard card = new DebitCard("RUB", 1000, "Дебетовая карта");
        card.deposit(500);
        Assert.assertEquals(card.getBalance(), 1500);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDebitCardWithdrawException() {
        DebitCard card = new DebitCard("RUB", 1000, "Дебетовая карта");
        card.withdraw(2000);
    }
}