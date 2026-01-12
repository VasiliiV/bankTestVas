package ru.bank.app.repository

import ru.bank.app.model.Transaction
import java.util.UUID

/**
 * Репозиторий транзакций для истории операций.
 */
interface TransactionRepository {
    /**
     * Сохраняет транзакцию.
     */
    fun save(transaction: Transaction)

    /**
     * Возвращает последние операции по ID пользователя.
     */
    fun findRecentByUserId(userId: UUID, limit: Int): List<Transaction>
}
