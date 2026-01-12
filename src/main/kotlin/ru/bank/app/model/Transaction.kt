package ru.bank.app.model

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

/**
 * Транзакция между счетами.
 *
 * @property id уникальный идентификатор операции.
 * @property userId пользователь, инициировавший операцию.
 * @property fromAccountId счёт отправителя.
 * @property toAccountId счёт получателя.
 * @property amount сумма перевода.
 * @property createdAt дата и время выполнения операции.
 */
data class Transaction(
    val id: UUID,
    val userId: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: BigDecimal,
    val createdAt: Instant
)
