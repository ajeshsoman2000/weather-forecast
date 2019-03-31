package com.example.weatherforecast.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val weatherDetail: MutableLiveData<WeatherResponse> = MutableLiveData()
    lateinit var selectedForecast: Forecast
    private var weatherResponse: WeatherResponse? = null
    val backgroundScope = CoroutineScope(Dispatchers.IO)
    val mainScope = CoroutineScope(Dispatchers.Main)
    var loggedInUser: UserDetailEntity? = null

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