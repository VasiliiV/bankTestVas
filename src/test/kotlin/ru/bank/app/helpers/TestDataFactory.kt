package ru.bank.app.helpers

import ru.bank.app.core.AppContainer
import ru.bank.app.model.Account
import ru.bank.app.model.User
import java.math.BigDecimal
import java.util.UUID

/**
 * Набор хелперов для подготовки тестовых данных.
 */
object TestDataFactory {
    /**
     * Создаёт пользователя и набор счетов для сценариев тестирования.
     */
    fun createUserWithAccounts(
        phone: String = "+7" + UUID.randomUUID().toString().take(10),
        pin: String = "1234",
        balances: List<BigDecimal> = listOf(BigDecimal("15000"), BigDecimal("5000"))
    ): Pair<User, List<Account>> {
        val user = AppContainer.authService.register(phone, pin)
        val accounts = balances.mapIndexed { index, balance ->
            AppContainer.bankingService.createAccount(
                userId = user.id,
                title = "Счёт ${index + 1}",
                initialBalance = balance
            )
        }
        return user to accounts
    }
}
