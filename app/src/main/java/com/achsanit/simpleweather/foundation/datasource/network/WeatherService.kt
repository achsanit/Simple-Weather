package com.achsanit.simpleweather.foundation.datasource.network

import com.achsanit.simpleweather.foundation.datasource.network.response.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @QueryMap queryParams: HashMap<String,String>
    ): CurrentWeatherResponse

}