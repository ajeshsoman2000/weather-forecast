package com.example.weatherforecast.utils

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DateFormatUtilsKtTest {

    @Test
    fun changeDateFormat() {
        val date = "2019-01-29"
        val oldFormat = "yyyy-MM-dd"
        val newFormat = "dd-MM-yyyy"
        val expectedDate = "29-01-2019"
        val newDate = date.changeDateFormat(oldFormat, newFormat)
        assertEquals(expectedDate, newDate)
    }

    @Test
    fun getDayFromDate_success() {
        val date = "2019-01-29"
        val day = date.getDayFromDate("yyyy-MM-dd")
        Assert.assertEquals("Tuesday", day)
    }

    @Test
    fun getDayFromDate_error() {
        val date = "2019-01-29"
        val day = date.getDayFromDate("yyyy-MM-dd")
        Assert.assertNotEquals("Monday", day)
    }
}