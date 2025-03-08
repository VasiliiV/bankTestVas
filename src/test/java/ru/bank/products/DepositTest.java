package ru.bank.products;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DepositTest {

    @Test
    @Description("Проверка закрытия вклада: баланс должен обнулиться, а вклад помечен закрытым")
    public void testDepositClose() {
        Deposit deposit = new Deposit("RUB", 5000, "Вклад");
        deposit.close();
        Assert.assertTrue(deposit.isClosed());
        Assert.assertEquals(deposit.getBalance(), 0.0);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    @Description("Проверка исключения при попытке пополнить закрытый вклад")
    public void testDepositAfterCloseException() {
        Deposit deposit = new Deposit("RUB", 5000, "Вклад");
        deposit.close();
        deposit.deposit(1000);
    }
}