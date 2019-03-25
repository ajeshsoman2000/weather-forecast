package com.example.weatherforecast.model

sealed class LoginResponse {
    class Success(sucessMsg: String): LoginResponse()
    class Error(errorMsg: String): LoginResponse()
}