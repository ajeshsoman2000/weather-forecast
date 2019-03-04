package com.example.weatherforecast.repository

import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.service.WeatherForecastService

class WeatherRepository {

    suspend fun getWeatherForecast(city: String): WeatherResponse {
        return WeatherForecastService().fetch(city)
    }
}