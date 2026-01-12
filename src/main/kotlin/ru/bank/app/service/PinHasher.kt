package ru.bank.app.service

import java.security.MessageDigest

/**
 * Сервис для хэширования ПИН-кода.
 */
class PinHasher {
    /**
     * Преобразует ПИН-код в строку SHA-256 для безопасного хранения.
     */
    fun hash(pin: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(pin.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Сверяет ПИН-код с сохранённым хэшем.
     */
    fun matches(pin: String, hash: String): Boolean = hash(pin) == hash
}
