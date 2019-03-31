package com.example.weatherforecast.repository

import com.example.weatherforecast.model.LoginResponse
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.database.userdetail.UserDetailDao
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import com.example.weatherforecast.repository.service.WeatherForecastService
import com.example.weatherforecast.utils.hashPassword

class WeatherRepository {

    suspend fun getWeatherForecast(city: String): WeatherResponse {
        return WeatherForecastService().fetch(city)
    }

    suspend fun getUserFromDB(email: String, password: String, dao: UserDetailDao?): LoginResponse {
        val registeredUser = dao?.getUser(email)
        return if (registeredUser != null) {
            if (password.hashPassword() == registeredUser.password) {
                LoginResponse.Success(registeredUser)
            } else {
                LoginResponse.Error("Invalid credentials")
            }
        } else {
            LoginResponse.Error("No such user.")
        }
    }

    suspend fun addUserToDB(email: String, password: String, name: String, dao: UserDetailDao?) {
        dao?.addUser(UserDetailEntity(email, name, password.hashPassword()))
    }
}