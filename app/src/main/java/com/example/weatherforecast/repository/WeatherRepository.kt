package com.example.weatherforecast.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.service.WeatherForecastService

class WeatherRepository() {

    fun getWeatherForecast(city: String, listener: UpdateUIListener): LiveData<WeatherModel> {
        return WeatherForecastService().fetch(city, listener)
    }
}