package ru.bank.app.model

/**
 * Модель домашнего экрана мобильного банка.
 *
 * @property accounts список пользовательских счетов.
 * @property recentTransactions последние операции для быстрого обзора.
 */
data class Dashboard(
    val accounts: List<Account>,
    val recentTransactions: List<Transaction>
)
