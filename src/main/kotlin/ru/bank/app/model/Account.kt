package ru.bank.app.model

import java.math.BigDecimal
import java.util.UUID

/**
 * Банковский счёт пользователя.
 *
 * @property id уникальный идентификатор счёта.
 * @property userId владелец счёта.
 * @property title маркетинговое название счёта.
 * @property balance текущий баланс в основной валюте.
 */
data class Account(
    val id: UUID,
    val userId: UUID,
    val title: String,
    val balance: BigDecimal
)
