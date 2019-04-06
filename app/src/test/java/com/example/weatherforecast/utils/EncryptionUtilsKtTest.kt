package com.example.weatherforecast.utils

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EncryptionUtilsKtTest {

    @Test
    fun hashPassword_success() {
        val hashedPassword = "12345".hashPassword().trim()
        assertEquals("g4MCaU7GcQ4oc9qsvsjD7Q==", hashedPassword)
    }

    @Test
    fun hashPassword_error() {
        val hashedPassword = "123456".hashPassword().trim()
        assertNotEquals("g4MCaU7GcQ4oc9qsvsjD7Q==", hashedPassword)
    }
}