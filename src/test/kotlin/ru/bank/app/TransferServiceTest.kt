package ru.bank.app

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import ru.bank.app.core.AppContainer
import ru.bank.app.helpers.TestDataFactory
import java.math.BigDecimal

class TransferServiceTest {
    /**
     * Тест-кейс: перевод между своими счетами с корректным списанием и зачислением.
     * Ожидаем сохранение транзакции и обновление балансов.
     */
    @Test
    fun shouldTransferBetweenAccounts() {
        val (user, accounts) = TestDataFactory.createUserWithAccounts(
            balances = listOf(BigDecimal("10000"), BigDecimal("2000"))
        )
        val from = accounts[0]
        val to = accounts[1]

        val transaction = AppContainer.bankingService.transfer(
            userId = user.id,
            fromAccountId = from.id,
            toAccountId = to.id,
            amount = BigDecimal("1500")
        )

        val dashboard = AppContainer.bankingService.getDashboard(user.id)
        val updatedFrom = dashboard.accounts.first { it.id == from.id }
        val updatedTo = dashboard.accounts.first { it.id == to.id }

        assertEquals(updatedFrom.balance, BigDecimal("8500"))
        assertEquals(updatedTo.balance, BigDecimal("3500"))
        assertEquals(transaction.amount, BigDecimal("1500"))
    }
}
