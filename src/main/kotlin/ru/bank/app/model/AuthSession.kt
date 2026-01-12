package ru.bank.app.model

import java.time.Instant
import java.util.UUID

/**
 * Результат аутентификации пользователя.
 *
 * @property userId идентификатор пользователя.
 * @property accessToken JWT для коротких запросов.
 * @property refreshToken JWT для обновления сессии.
 * @property expiresAt время истечения access токена.
 */
data class AuthSession(
    val userId: UUID,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Instant
)
