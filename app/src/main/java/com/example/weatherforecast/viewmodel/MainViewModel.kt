package com.example.weatherforecast.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.LoginResponse
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import com.example.weatherforecast.repository.database.userdetail.UserDetailDB
import com.example.weatherforecast.repository.database.userdetail.UserDetailDao
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import com.example.weatherforecast.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val weatherDetail: MutableLiveData<WeatherResponse> = MutableLiveData()
    lateinit var selectedForecast: Forecast
    private var weatherResponse: WeatherResponse? = null
    val backgroundScope = CoroutineScope(Dispatchers.IO)
    val mainScope = CoroutineScope(Dispatchers.Main)
    var loggedInUser: UserDetailEntity? = null
    private var db: UserDetailDB? = UserDetailDB.getAppDataBase(context = getApplication())
    private var userDao: UserDetailDao? = db?.userDetailDao()
    private val weatherRepository = WeatherRepository()

    fun getWeatherForecast(): LiveData<WeatherResponse> {
        return weatherDetail
    }

    suspend fun updateWeatherForecast(city: String) {
        if (city.isNotEmpty()) {
            weatherResponse = weatherRepository.getWeatherForecast(city)
            weatherDetail.postValue(weatherResponse)
        } else {
            weatherDetail.postValue(WeatherResponse.Error("Please enter a city."))
        }
    }

    suspend fun authenticateUser(email: String, password: String): LoginResponse =
            weatherRepository.getUserFromDB(email, password, userDao)

    suspend fun registerUser(email: String, password: String, name: String) =
        weatherRepository.addUserToDB(email, password, name, userDao)

}