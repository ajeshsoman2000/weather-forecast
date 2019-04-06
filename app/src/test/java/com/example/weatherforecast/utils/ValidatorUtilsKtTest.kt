package com.example.weatherforecast.utils

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ValidatorUtilsKtTest {

    @Test
    fun isValidEmail_success() {
       val validEmail = "123@456.com"
        assertTrue(validEmail.isValidEmail())
    }

    @Test
    fun isValidEmail_success_different_format() {
        val validEmail = "123@456.co.in"
        assertTrue(validEmail.isValidEmail())
    }

    @Test
    fun isValidEmail_error() {
        val inValidEmail = "123@456"
        assertFalse(inValidEmail.isValidEmail())
    }
}