package ru.bank.app.service

import ru.bank.app.model.Account
import ru.bank.app.model.Dashboard
import ru.bank.app.model.Transaction
import ru.bank.app.repository.AccountRepository
import ru.bank.app.repository.TransactionRepository
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

/**
 * Сервис бизнес-операций мобильного банка.
 */
class BankingService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    /**
     * Создаёт новый счёт пользователя.
     */
    fun createAccount(userId: UUID, title: String, initialBalance: BigDecimal): Account {
        val account = Account(UUID.randomUUID(), userId, title, initialBalance)
        accountRepository.save(account)
        return account
    }

    /**
     * Возвращает данные для главного экрана приложения.
     */
    fun getDashboard(userId: UUID): Dashboard {
        val accounts = accountRepository.findByUserId(userId)
        val transactions = transactionRepository.findRecentByUserId(userId, limit = 10)
        return Dashboard(accounts, transactions)
    }

    /**
     * Выполняет перевод между счетами пользователя.
     */
    fun transfer(userId: UUID, fromAccountId: UUID, toAccountId: UUID, amount: BigDecimal): Transaction {
        require(amount > BigDecimal.ZERO) { "Сумма должна быть положительной" }
        synchronized(this) {
            val fromAccount = accountRepository.findById(fromAccountId)
                ?: error("Счёт отправителя не найден")
            val toAccount = accountRepository.findById(toAccountId)
                ?: error("Счёт получателя не найден")
            if (fromAccount.userId != userId) {
                error("Нет доступа к счёту отправителя")
            }
            if (fromAccount.balance < amount) {
                error("Недостаточно средств")
            }
            val updatedFrom = fromAccount.copy(balance = fromAccount.balance - amount)
            val updatedTo = toAccount.copy(balance = toAccount.balance + amount)
            accountRepository.save(updatedFrom)
            accountRepository.save(updatedTo)
            val transaction = Transaction(
                id = UUID.randomUUID(),
                userId = userId,
                fromAccountId = fromAccountId,
                toAccountId = toAccountId,
                amount = amount,
                createdAt = Instant.now()
            )
            transactionRepository.save(transaction)
            return transaction
        }
    }
}
