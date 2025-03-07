package ru.bank.products;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DepositTest {

    @Test
    public void testDepositClose() {
        Deposit deposit = new Deposit("RUB", 5000, "Вклад");
        deposit.close();
        Assert.assertTrue(deposit.isClosed());
        Assert.assertEquals(deposit.getBalance(), 0.0);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testDepositAfterCloseException() {
        Deposit deposit = new Deposit("RUB", 5000, "Вклад");
        deposit.close();
        deposit.deposit(1000);
    }
}