package ru.bank.products;

import io.qameta.allure.Description;
import java.math.BigDecimal;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DebitCardTest {

    @Test
    @Description("Проверка корректного пополнения баланса дебетовой карты")
    public void testDebitCardDeposit() {
        DebitCard card = new DebitCard("RUB", BigDecimal.valueOf(1000), "Дебетовая карта");
        card.deposit(BigDecimal.valueOf(500));
        Assert.assertEquals(card.getBalance(), BigDecimal.valueOf(1500));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    @Description("Проверка исключения при попытке списания суммы больше доступного баланса на дебетовой карте")
    public void testDebitCardWithdrawException() {
        DebitCard card = new DebitCard("RUB", BigDecimal.valueOf(1000), "Дебетовая карта");
        card.withdraw(BigDecimal.valueOf(2000));
    }
}
