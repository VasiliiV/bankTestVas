package ru.bank.app

import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test
import ru.bank.app.core.AppContainer
import java.util.UUID

class AuthServiceTest {
    /**
     * Тест-кейс: успешный логин по номеру телефона и ПИН-коду.
     * Ожидаем выдачу access и refresh токенов.
     */
    @Test
    fun shouldLoginAndIssueTokens() {
        val phone = "+7" + UUID.randomUUID().toString().take(10)
        val pin = "1357"
        val user = AppContainer.authService.register(phone, pin)

        val session = AppContainer.authService.login(phone, pin)

        assertEquals(session.userId, user.id)
        assertNotNull(session.accessToken)
        assertNotNull(session.refreshToken)
    }

    /**
     * Тест-кейс: проверка access токена через Redis-слой.
     * Ожидаем, что проверка вернёт идентификатор пользователя.
     */
    @Test
    fun shouldVerifyAccessToken() {
        val phone = "+7" + UUID.randomUUID().toString().take(10)
        val pin = "2468"
        val user = AppContainer.authService.register(phone, pin)
        val session = AppContainer.authService.login(phone, pin)

        val verifiedUserId = AppContainer.authService.verifyAccessToken(session.accessToken)

        assertEquals(verifiedUserId, user.id)
    }
}
