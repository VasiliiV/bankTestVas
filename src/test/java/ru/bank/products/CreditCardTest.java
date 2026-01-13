package ru.bank.products;

import io.qameta.allure.Description;
import java.math.BigDecimal;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreditCardTest {

    @Test
    @Description("Проверка корректного расчета задолженности после снятия средств с кредитной карты")
    public void testCreditCardDebt() {
        CreditCard card =
                new CreditCard("RUB", BigDecimal.ZERO, "Кредитная карта", BigDecimal.valueOf(15));
        card.withdraw(BigDecimal.valueOf(500));
        Assert.assertEquals(card.getDebt(), BigDecimal.valueOf(500));
    }

    @Test
    @Description("Проверка корректного получения процентной ставки по кредитной карте")
    public void testInterestRate() {
        CreditCard card =
                new CreditCard("RUB", BigDecimal.ZERO, "Кредитная карта", BigDecimal.valueOf(15));
        Assert.assertEquals(card.getInterestRate(), BigDecimal.valueOf(15));
    }
}
