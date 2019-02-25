package com.example.weatherforecast

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.viewmodel.MainViewModel
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito



class MainViewModelUnitTest {

    @Mock
    private lateinit var mockViewModel: MainViewModel
    @Mock
    private lateinit var mockWeatherModel: WeatherModel

    private val backgroundScope = CoroutineScope(Dispatchers.Default)

    @Before
    fun setup() {
        mockViewModel = Mockito.mock(MainViewModel::class.java)
        mockWeatherModel = Mockito.mock(WeatherModel::class.java)
    }

    @Test
    fun getForecast_withoutCity() {
        Mockito.`when`(mockViewModel.getWeatherForecast()).thenReturn(null)
        val response = mockViewModel.getWeatherForecast()
        Assert.assertNull(response)
    }

    @Test
    fun getForecast_withCity() {
        val mockLiveData = MutableLiveData<WeatherResponse>()
        Mockito.`when`(mockViewModel.getWeatherForecast()).thenReturn(mockLiveData)
        val response = mockViewModel.getWeatherForecast()
        Assert.assertNotNull(response)
    }
}