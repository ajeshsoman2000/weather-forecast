package com.example.weatherforecast.utils

import org.junit.Test

import org.junit.Assert.*

class ValidatorUtilsKtTest {

    @Test
    fun isValidEmail() {
       val validEmail = "123@456.com"
        assertTrue(validEmail.isValidEmail())
    }
}