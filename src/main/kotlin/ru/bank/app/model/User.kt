package ru.bank.app.model

import java.util.UUID

/**
 * Представляет пользователя мобильного банка.
 *
 * @property id уникальный идентификатор пользователя.
 * @property phone номер телефона, используемый для входа.
 * @property pinHash хэш ПИН-кода для проверки доступа.
 */
data class User(
    val id: UUID,
    val phone: String,
    val pinHash: String
)
