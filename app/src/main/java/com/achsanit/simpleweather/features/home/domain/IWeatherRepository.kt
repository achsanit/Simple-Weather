package com.achsanit.simpleweather.features.home.domain

import com.achsanit.simpleweather.foundation.utils.Resource
import com.achsanit.simpleweather.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    fun getCurrentWeather(lat: String, lon: String): Flow<Resource<CurrentWeather>>
    fun getWeatherCity(lat: String, lon: String): Flow<Resource<List<CurrentWeather>>>
}