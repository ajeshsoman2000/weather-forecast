package com.example.weatherforecast.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.service.WeatherForecastService

class WeatherRepository() {

    fun getWeatherForecast(city: String) {
        WeatherForecastService().fetch(city)
    }
}