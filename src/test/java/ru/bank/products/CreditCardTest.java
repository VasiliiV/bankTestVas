package ru.bank.products;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreditCardTest {

    @Test
    @Description("Проверка корректного расчета задолженности после снятия средств с кредитной карты")
    public void testCreditCardDebt() {
        CreditCard card = new CreditCard("RUB", 0, "Кредитная карта", 15);
        card.withdraw(500);
        Assert.assertEquals(card.getDebt(), 500);
    }

    @Test
    @Description("Проверка корректного получения процентной ставки по кредитной карте")
    public void testInterestRate() {
        CreditCard card = new CreditCard("RUB", 0, "Кредитная карта", 15);
        Assert.assertEquals(card.getInterestRate(), 15.0);
    }
}