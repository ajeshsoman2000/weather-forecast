package com.example.weatherforecast.model

enum class RegistrationResponse(var msg: String) {
    SUCCESS("User successfully registered."),
    INVALID_EMAIL("Invalid email format."),
    EMPTY_FIELDS("All fields are mandatory.")
}