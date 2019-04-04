package com.example.weatherforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.viewmodel.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito



class MainViewModelUnitTest {

    @Mock
    private lateinit var mockViewModel: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockViewModel = Mockito.mock(MainViewModel::class.java)
    }

    @Test
    fun getForecast_withoutCity() {
        val mockErrorResponse = Mockito.mock(WeatherResponse.Error::class.java)
        val mockLivedataResponse = MutableLiveData<WeatherResponse>()
        mockLivedataResponse.postValue(mockErrorResponse)
        runBlocking {
            mockViewModel.updateWeatherForecast("")
                Mockito.`when`(mockViewModel.getWeatherForecast()).thenReturn(mockLivedataResponse)
        }
        val actualResponse = mockViewModel.getWeatherForecast()
        Assert.assertEquals(mockErrorResponse, actualResponse.value)
    }

    @Test
    fun getForecast_withCity() {
        val dummyResponse = Gson().fromJson<WeatherModel>(weatherSuccess, WeatherModel::class.java)
        val mockSuccessResponse = WeatherResponse.Success(dummyResponse)
        val mockLivedataResponse = MutableLiveData<WeatherResponse>()
        mockLivedataResponse.postValue(mockSuccessResponse)
        runBlocking {
            mockViewModel.updateWeatherForecast("London")
            Mockito.`when`(mockViewModel.getWeatherForecast()).thenReturn(mockLivedataResponse)
        }
        val actualResponse = mockViewModel.getWeatherForecast()
        Assert.assertEquals(mockSuccessResponse, actualResponse.value)

        val observer = Observer<WeatherResponse> {
            Assert.assertEquals(mockSuccessResponse, it)
        }

        mockViewModel.getWeatherForecast().observeForever(observer)
    }

}