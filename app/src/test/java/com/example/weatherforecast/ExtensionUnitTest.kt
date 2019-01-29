package com.example.weatherforecast

import com.example.weatherforecast.utils.changeDateFormat
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExtensionUnitTest {

    @Test
    fun changeDateFormat_test() {
        val date = "2019-01-29"
        val oldFormat = "yyyy-MM-dd"
        val newFormat = "dd-MM-yyyy"
        val expectedDate = "29-01-2019"
        val newDate = date.changeDateFormat(oldFormat, newFormat)
        assertEquals(expectedDate, newDate)
    }
}
