package ru.bank.app.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.time.Instant
import java.util.Date
import java.util.UUID

/**
 * Генератор и верификатор JWT токенов для мобильного приложения.
 */
class JwtService(
    private val issuer: String,
    private val secret: String
) {
    private val algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /**
     * Создаёт access-токен с коротким сроком жизни.
     */
    fun issueAccessToken(userId: UUID, expiresAt: Instant): String =
        issueToken(userId, "access", expiresAt)

    /**
     * Создаёт refresh-токен для обновления сессии.
     */
    fun issueRefreshToken(userId: UUID, expiresAt: Instant): String =
        issueToken(userId, "refresh", expiresAt)

    /**
     * Проверяет токен и возвращает ID пользователя.
     */
    fun verify(token: String): UUID {
        val decoded = verifier.verify(token)
        return UUID.fromString(decoded.subject)
    }

    private fun issueToken(userId: UUID, type: String, expiresAt: Instant): String {
        return JWT.create()
            .withIssuer(issuer)
            .withSubject(userId.toString())
            .withClaim("type", type)
            .withExpiresAt(Date.from(expiresAt))
            .withIssuedAt(Date())
            .sign(algorithm)
    }
}
