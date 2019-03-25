package com.example.weatherforecast.utils

import java.security.SecureRandom
import android.R.attr.password
import android.util.Base64
import java.nio.charset.Charset
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


fun String.hashPassword(): String {

    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)
    val spec = PBEKeySpec(this.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash = factory.generateSecret(spec).encoded
    return String(hash, Charset.defaultCharset())
}