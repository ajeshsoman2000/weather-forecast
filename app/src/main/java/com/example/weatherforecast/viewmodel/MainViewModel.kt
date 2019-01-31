package com.example.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherRepository

class MainViewModel: ViewModel() {

    var weatherDetail: LiveData<WeatherModel>? = null
    lateinit var selectedForecast: Forecast

    fun getWeatherForecast(city: String, listener: UpdateUIListener): LiveData<WeatherModel>? {
        return if (city.isNotEmpty()) {
            weatherDetail = WeatherRepository().getWeatherForecast(city, listener)
            weatherDetail as LiveData<WeatherModel>
        } else {
            null
        }
    }


}