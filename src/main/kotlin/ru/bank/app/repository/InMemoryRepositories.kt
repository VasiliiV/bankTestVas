package ru.bank.app.repository

import ru.bank.app.model.Account
import ru.bank.app.model.Transaction
import ru.bank.app.model.User
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * In-memory реализация UserRepository.
 *
 * Используется в демо-стенде вместо реальной БД.
 */
object InMemoryUserRepository : UserRepository {
    private val usersById = ConcurrentHashMap<UUID, User>()
    private val usersByPhone = ConcurrentHashMap<String, User>()

    override fun save(user: User) {
        usersById[user.id] = user
        usersByPhone[user.phone] = user
    }

    override fun findByPhone(phone: String): User? = usersByPhone[phone]

    override fun findById(id: UUID): User? = usersById[id]
}

/**
 * In-memory реализация AccountRepository.
 */
object InMemoryAccountRepository : AccountRepository {
    private val accountsById = ConcurrentHashMap<UUID, Account>()
    private val accountsByUser = ConcurrentHashMap<UUID, MutableList<Account>>()

    override fun save(account: Account) {
        accountsById[account.id] = account
        accountsByUser.computeIfAbsent(account.userId) { CopyOnWriteArrayList() }
            .apply {
                removeIf { it.id == account.id }
                add(account)
            }
    }

    override fun findByUserId(userId: UUID): List<Account> =
        accountsByUser[userId]?.toList().orEmpty()

    override fun findById(id: UUID): Account? = accountsById[id]
}

/**
 * In-memory реализация TransactionRepository.
 */
object InMemoryTransactionRepository : TransactionRepository {
    private val transactions = CopyOnWriteArrayList<Transaction>()

    override fun save(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun findRecentByUserId(userId: UUID, limit: Int): List<Transaction> {
        return transactions
            .filter { it.userId == userId }
            .sortedByDescending { it.createdAt }
            .take(limit)
    }
}
