package com.achsanit.simpleweather.foundation.datasource.mapper

import com.achsanit.simpleweather.foundation.datasource.local.model.WeatherEntity
import com.achsanit.simpleweather.foundation.datasource.network.response.CurrentWeatherResponse
import com.achsanit.simpleweather.model.CurrentWeather

fun WeatherEntity?.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        currentTime = this?.currentTime ?: 0,
        icon =  this?.icon ?: "",
        locationName = this?.locationName ?: "",
        weather = this?.weather ?: "",
        weatherDesc = this?.weatherDesc ?: "",
        temperature = this?.temperature ?: 0f,
        latestUpdate = this?.updatedAt ?: 0L,
        feelsLike = this?.feelsLike ?: 0f,
        humidity = this?.humidity ?: 0,
        windSpeed = this?.windSpeed ?: 0f,
        lat = this?.lat ?: 0.0,
        long = this?.long ?: 0.0
    )
}

fun CurrentWeatherResponse.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        currentTime = dt ?: 0,
        icon = weather?.firstOrNull()?.icon ?: "",
        locationName = name ?: "",
        weather = weather?.firstOrNull()?.main ?: "",
        weatherDesc = weather?.firstOrNull()?.description ?: "",
        temperature = main?.temp ?: 0f,
        feelsLike = main?.feelsLike ?: 0f,
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0f,
        lat = coord?.lat ?: 0.0,
        long = coord?.lon ?: 0.0
    )
}
fun CurrentWeatherResponse.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        currentTime = dt ?: 0,
        icon = weather?.firstOrNull()?.icon ?: "",
        locationName = name ?: "",
        weather = weather?.firstOrNull()?.main ?: "",
        weatherDesc = weather?.firstOrNull()?.description ?: "",
        temperature = main?.temp ?: 0f,
        feelsLike = main?.feelsLike ?: 0f,
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0f,
        lat = coord?.lat ?: 0.0,
        long = coord?.lon ?: 0.0
    )
}