package com.example.weatherforecast.utils

import android.util.Base64
import java.nio.charset.Charset
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val salt = ByteArray(16)
fun String.hashPassword(): String {
    val spec = PBEKeySpec(this.toCharArray(), salt, 65536, 128)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash = factory.generateSecret(spec).encoded
    return Base64.encodeToString(hash, Base64.DEFAULT)
}