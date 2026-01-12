package ru.bank.app.service

import java.time.Instant
import java.util.UUID

/**
 * Хранилище токенов для отзыва доступа.
 */
interface TokenStore {
    /**
     * Сохраняет активные токены пользователя.
     */
    fun store(userId: UUID, accessToken: String, refreshToken: String, expiresAt: Instant)

    /**
     * Проверяет, активен ли токен пользователя.
     */
    fun isActive(userId: UUID, token: String): Boolean
}

/**
 * Симуляция Redis-хранилища токенов.
 *
 * В продакшене вместо карты используется Redis для быстрого отзыва токенов.
 */
class InMemoryRedisTokenStore : TokenStore {
    private data class TokenEntry(
        val accessToken: String,
        val refreshToken: String,
        val expiresAt: Instant
    )

    private val storage = mutableMapOf<UUID, TokenEntry>()

    override fun store(userId: UUID, accessToken: String, refreshToken: String, expiresAt: Instant) {
        storage[userId] = TokenEntry(accessToken, refreshToken, expiresAt)
    }

    override fun isActive(userId: UUID, token: String): Boolean {
        val entry = storage[userId] ?: return false
        return (entry.accessToken == token || entry.refreshToken == token) && entry.expiresAt.isAfter(Instant.now())
    }
}
