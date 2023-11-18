package com.achsanit.simpleweather.features.home.domain

import com.achsanit.simpleweather.foundation.utils.Resource
import com.achsanit.simpleweather.model.CurrentWeather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherInteractor @Inject constructor(
    private val repository: IWeatherRepository
): WeatherUseCase {
    override fun getCurrentWeather(lat: String, lon: String): Flow<Resource<CurrentWeather>> =
        repository.getCurrentWeather(lat, lon)

    override fun getWeatherCity(lat: String, lon: String): Flow<Resource<List<CurrentWeather>>> =
        repository.getWeatherCity(lat, lon)

}