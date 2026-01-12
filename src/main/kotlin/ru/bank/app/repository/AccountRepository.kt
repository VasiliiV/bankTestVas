package ru.bank.app.repository

import ru.bank.app.model.Account
import java.util.UUID

/**
 * Репозиторий счетов пользователя.
 */
interface AccountRepository {
    /**
     * Сохраняет счет.
     */
    fun save(account: Account)

    /**
     * Возвращает все счета пользователя.
     */
    fun findByUserId(userId: UUID): List<Account>

    /**
     * Ищет счёт по ID.
     */
    fun findById(id: UUID): Account?
}
