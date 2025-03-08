package ru.bank.products;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DebitCardTest {

    @Test
    @Description("Проверка корректного пополнения баланса дебетовой карты")
    public void testDebitCardDeposit() {
        DebitCard card = new DebitCard("RUB", 1000, "Дебетовая карта");
        card.deposit(500);
        Assert.assertEquals(card.getBalance(), 1500);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    @Description("Проверка исключения при попытке списания суммы больше доступного баланса на дебетовой карте")
    public void testDebitCardWithdrawException() {
        DebitCard card = new DebitCard("RUB", 1000, "Дебетовая карта");
        card.withdraw(2000);
    }
}