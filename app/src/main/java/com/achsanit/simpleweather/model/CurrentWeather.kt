package com.achsanit.simpleweather.model

data class CurrentWeather(
    val currentTime: Int,
    val icon: String,
    val locationName: String,
    val weather: String,
    val weatherDesc: String,
    val temperature: Float,
    val latestUpdate: Long? = null,
    val feelsLike: Float,
    val humidity: Int,
    val windSpeed: Float,
    val lat: Double,
    val long: Double,
)
