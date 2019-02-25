package com.example.weatherforecast

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherforecast.utils.getDayFromDate
import com.example.weatherforecast.utils.isNetworkConnectionAvailable
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
    @MediumTest
class ForecastInstrumentedTest {

    private var instrumentationCtx: Context? = null

    @Before
    fun setup() {
        instrumentationCtx = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
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

    @Test
    fun connectivity_test() {
        assertTrue(instrumentationCtx?.isNetworkConnectionAvailable()!!)
    }
}
