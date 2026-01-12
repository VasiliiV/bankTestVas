package ru.bank.app.core

import ru.bank.app.repository.InMemoryAccountRepository
import ru.bank.app.repository.InMemoryTransactionRepository
import ru.bank.app.repository.InMemoryUserRepository
import ru.bank.app.service.AuthService
import ru.bank.app.service.BankingService
import ru.bank.app.service.InMemoryRedisTokenStore
import ru.bank.app.service.JwtService
import ru.bank.app.service.PinHasher

/**
 * Singleton-контейнер зависимостей демо-приложения.
 */
object AppContainer {
    private val pinHasher = PinHasher()
    private val tokenStore = InMemoryRedisTokenStore()
    private val jwtService = JwtService(
        issuer = "bank-demo",
        secret = "demo-secret-change-in-production"
    )

    /**
     * Сервис аутентификации.
     */
    val authService: AuthService = AuthService(
        userRepository = InMemoryUserRepository,
        pinHasher = pinHasher,
        jwtService = jwtService,
        tokenStore = tokenStore
    )

    /**
     * Сервис операций со счетами.
     */
    val bankingService: BankingService = BankingService(
        accountRepository = InMemoryAccountRepository,
        transactionRepository = InMemoryTransactionRepository
    )
}
