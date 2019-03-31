package com.example.weatherforecast.model

import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity

sealed class LoginResponse {
    class Success(val authenticatedUser: UserDetailEntity): LoginResponse()
    class Error(val errorMsg: String): LoginResponse()
}