package com.example.weatherforecast.utils

import java.nio.charset.Charset
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val salt = ByteArray(16)
fun String.hashPassword(): String {
/*    val random = SecureRandom()
    random.nextBytes(salt)*/
    val spec = PBEKeySpec(this.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash = factory.generateSecret(spec).encoded
    return String(hash, Charset.defaultCharset())
}