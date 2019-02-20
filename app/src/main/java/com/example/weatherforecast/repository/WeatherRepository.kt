package com.example.weatherforecast.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.service.WeatherForecastService

class WeatherRepository {

    suspend fun getWeatherForecast(city: String): LiveData<WeatherResponse> {
        return WeatherForecastService().fetch(city)
    }
}