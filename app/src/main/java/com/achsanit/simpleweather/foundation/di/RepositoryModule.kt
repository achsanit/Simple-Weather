package com.achsanit.simpleweather.foundation.di

import android.content.Context
import com.achsanit.simpleweather.features.home.domain.IWeatherRepository
import com.achsanit.simpleweather.foundation.datasource.WeatherRepository
import com.achsanit.simpleweather.foundation.datasource.local.WeatherDao
import com.achsanit.simpleweather.foundation.datasource.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        weatherService: WeatherService,
        weatherDao: WeatherDao
    ): IWeatherRepository {
        return WeatherRepository(context,weatherService,weatherDao)
    }

}