package com.example.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainViewModel: ViewModel() {

    private val weatherDetail: MutableLiveData<WeatherResponse> = MutableLiveData()
    lateinit var selectedForecast: Forecast
    private var weatherResponse: WeatherResponse? = null
    val backgroundScope = CoroutineScope(Dispatchers.IO)
    val mainScope = CoroutineScope(Dispatchers.Main)

    fun getWeatherForecast(): LiveData<WeatherResponse> {
        return weatherDetail
    }

    suspend fun updateWeatherForecast(city: String) {
        if (city.isNotEmpty()) {
            weatherResponse = WeatherRepository().getWeatherForecast(city)
            weatherDetail.postValue(weatherResponse)
        } else {
            weatherDetail.postValue(WeatherResponse.Error("Please enter a city."))
        }
    }

}