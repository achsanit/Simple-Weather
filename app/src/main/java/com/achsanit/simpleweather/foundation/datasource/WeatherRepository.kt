package com.achsanit.simpleweather.foundation.datasource

import android.content.Context
import com.achsanit.simpleweather.BuildConfig
import com.achsanit.simpleweather.features.home.domain.IWeatherRepository
import com.achsanit.simpleweather.foundation.datasource.local.WeatherDao
import com.achsanit.simpleweather.foundation.datasource.mapper.toCurrentWeather
import com.achsanit.simpleweather.foundation.datasource.mapper.toWeatherEntity
import com.achsanit.simpleweather.foundation.datasource.network.WeatherService
import com.achsanit.simpleweather.foundation.utils.CodeError
import com.achsanit.simpleweather.foundation.utils.InternetConnectionHelper
import com.achsanit.simpleweather.foundation.utils.Resource
import com.achsanit.simpleweather.foundation.utils.listCity
import com.achsanit.simpleweather.foundation.utils.networkBoundResource
import com.achsanit.simpleweather.model.CurrentWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val context: Context,
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
): IWeatherRepository {
    override fun getCurrentWeather(lat: String, lon: String): Flow<Resource<CurrentWeather>> =
        networkBoundResource(
            fetch = {
                val params = HashMap<String,String>()
                params["lat"] = lat
                params["lon"] = lon
                params["units"] = "metric"
                params["appid"] = BuildConfig.API_KEY_OPEN_WEATHER

                weatherService.getCurrentWeather(params)
            },
            query = {
                weatherDao.getCurrentWeather().map {
                    it.toCurrentWeather()
                }
            },
            saveFetchResult = {
                weatherDao.deleteAndInsertWeather(it.toWeatherEntity().copy(
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                ))
            },
            shouldFetch = {
                InternetConnectionHelper.isOnline(context)
            }
        )

    override fun getWeatherCity(lat: String, lon: String): Flow<Resource<List<CurrentWeather>>> {
        val params = HashMap<String, String>()
        params["units"] = "metric"
        params["appid"] = BuildConfig.API_KEY_OPEN_WEATHER

        return flow {
            try {
                val list = mutableListOf<CurrentWeather>()

                listCity.forEach {
                    params["q"] = it

                    list.add(
                        weatherService.getCurrentWeather(params).toCurrentWeather()
                    )
                }

                emit(Resource.Success(list))

            } catch (httpException: HttpException) {
                when (httpException.code()) {
                    in 400..499 -> {
                        emit(
                            Resource.Error(
                                httpException.message.toString() + "-${httpException.code()}",
                                httpException.code(),
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            Resource.ServerError(
                                httpException.message.toString() + "-${httpException.code()}",
                                httpException.code()
                            )
                        )
                    }

                    else -> {
                        emit(
                            Resource.Error(
                                httpException.message.toString() + "-${httpException.code()}",
                                httpException.code(),
                            )
                        )
                    }
                }
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString(), CodeError.NO_INTERNET_CONNECTION))
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString(), CodeError.REQUEST_TIME_OUT))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString(), CodeError.SOMETHING_WENT_WRONG))
            }
        }
    }
}