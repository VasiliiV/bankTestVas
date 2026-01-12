package ru.bank.app.repository

import ru.bank.app.model.User
import java.util.UUID

/**
 * Репозиторий пользователей, отвечает за доступ к данным авторизации.
 */
interface UserRepository {
    /**
     * Сохраняет пользователя в базе.
     */
    fun save(user: User)

    /**
     * Ищет пользователя по номеру телефона.
     */
    fun findByPhone(phone: String): User?

    /**
     * Возвращает пользователя по ID.
     */
    fun findById(id: UUID): User?
}
