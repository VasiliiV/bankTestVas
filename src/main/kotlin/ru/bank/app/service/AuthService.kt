package ru.bank.app.service

import ru.bank.app.model.AuthSession
import ru.bank.app.model.User
import ru.bank.app.repository.UserRepository
import java.time.Duration
import java.time.Instant
import java.util.UUID

/**
 * Сервис аутентификации и выдачи JWT токенов.
 */
class AuthService(
    private val userRepository: UserRepository,
    private val pinHasher: PinHasher,
    private val jwtService: JwtService,
    private val tokenStore: TokenStore,
    private val accessTtl: Duration = Duration.ofMinutes(15),
    private val refreshTtl: Duration = Duration.ofDays(7)
) {
    /**
     * Регистрирует пользователя в системе мобильного банка.
     */
    fun register(phone: String, pin: String): User {
        val user = User(UUID.randomUUID(), phone, pinHasher.hash(pin))
        userRepository.save(user)
        return user
    }

    /**
     * Выполняет вход и выдаёт пару токенов.
     */
    fun login(phone: String, pin: String): AuthSession {
        val user = userRepository.findByPhone(phone)
            ?: error("Пользователь не найден")
        if (!pinHasher.matches(pin, user.pinHash)) {
            error("Неверный ПИН-код")
        }
        return issueSession(user.id)
    }

    /**
     * Проверяет access токен на активность в Redis-слое.
     */
    fun verifyAccessToken(accessToken: String): UUID {
        val userId = jwtService.verify(accessToken)
        if (!tokenStore.isActive(userId, accessToken)) {
            error("Токен не активен")
        }
        return userId
    }

    /**
     * Обновляет сессию по refresh токену.
     */
    fun refresh(refreshToken: String): AuthSession {
        val userId = jwtService.verify(refreshToken)
        if (!tokenStore.isActive(userId, refreshToken)) {
            error("Refresh токен не активен")
        }
        return issueSession(userId)
    }

    private fun issueSession(userId: UUID): AuthSession {
        val expiresAt = Instant.now().plus(accessTtl)
        val accessToken = jwtService.issueAccessToken(userId, expiresAt)
        val refreshToken = jwtService.issueRefreshToken(userId, Instant.now().plus(refreshTtl))
        tokenStore.store(userId, accessToken, refreshToken, expiresAt)
        return AuthSession(userId, accessToken, refreshToken, expiresAt)
    }
}
