package com.example.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository

class MainViewModel: ViewModel() {

    var weatherDetail: LiveData<WeatherResponse>? = null
    lateinit var selectedForecast: Forecast

    suspend fun getWeatherForecast(city: String): LiveData<WeatherResponse>? {
        return if (city.isNotEmpty()) {
            weatherDetail = WeatherRepository().getWeatherForecast(city)
            weatherDetail as LiveData<WeatherResponse>
        } else {
            null
        }
    }


}