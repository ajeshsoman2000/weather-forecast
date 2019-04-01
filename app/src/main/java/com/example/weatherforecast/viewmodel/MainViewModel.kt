package com.example.weatherforecast.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.R
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.LoginResponse
import com.example.weatherforecast.model.RegistrationResponse
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import com.example.weatherforecast.repository.database.userdetail.UserDetailDB
import com.example.weatherforecast.repository.database.userdetail.UserDetailDao
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import com.example.weatherforecast.utils.isNetworkConnectionAvailable
import com.example.weatherforecast.utils.isValidEmail
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
    lateinit var registrationStatus: RegistrationResponse
                private set
    fun getWeatherForecast(): LiveData<WeatherResponse> {
        return weatherDetail
    }

    suspend fun updateWeatherForecast(city: String) {
        if ((getApplication() as Application).applicationContext.isNetworkConnectionAvailable()) {
            if (city.isNotEmpty()) {
                weatherResponse = weatherRepository.getWeatherForecast(city)
                weatherDetail.postValue(weatherResponse)
            } else {
                weatherDetail.postValue(WeatherResponse.Error(
                    (getApplication() as Application).applicationContext.getString(R.string.err_no_city_entered)))
            }
        } else {
            weatherDetail.postValue(WeatherResponse.Error((getApplication() as Application).
                applicationContext.getString(R.string.err_no_connectivity)))
        }
    }

    suspend fun authenticateUser(email: String, password: String): LoginResponse {
        return if (email.isNotEmpty() && password.isNotEmpty()) {
             if (email.isValidEmail()) {
                 weatherRepository.getUserFromDB(email, password, userDao)
             } else {
                 LoginResponse.Error((getApplication() as Application).
                     applicationContext.getString(R.string.err_invalid_email_format))
             }
        } else {
            LoginResponse.Error((getApplication() as Application).
                applicationContext.getString(R.string.err_all_fields_required))
        }
    }


    suspend fun registerUser(email: String, password: String, name: String) {
        return if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            if (email.isValidEmail()) {
                weatherRepository.addUserToDB(email, password, name, userDao)
                registrationStatus = RegistrationResponse.SUCCESS
            } else {
                registrationStatus = RegistrationResponse.INVALID_EMAIL
            }
        } else {
            registrationStatus = RegistrationResponse.EMPTY_FIELDS
        }
    }
}