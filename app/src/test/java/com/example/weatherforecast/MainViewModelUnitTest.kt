package com.example.weatherforecast

import android.content.Context
import com.example.weatherforecast.viewmodel.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito



class MainViewModelUnitTest {

    @Mock
    var mockUpdateUIListener: UpdateUIListener? = null

    @Before
    fun setup() {
        mockUpdateUIListener = Mockito.spy(UpdateUIListener::class.java)
    }

    @Test
    fun getForecast_withoutCity() {
        val viewModel = MainViewModel()
        val forecast = viewModel.getWeatherForecast("", mockUpdateUIListener!!)
        Assert.assertNull(forecast)
    }

    @Test
    fun getForecast_withCity() {
        val viewModel = MainViewModel()
        val forecast = viewModel.getWeatherForecast("London", mockUpdateUIListener!!)
        Assert.assertNotNull(forecast)
    }
}