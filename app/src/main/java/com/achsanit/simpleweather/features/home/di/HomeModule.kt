package com.achsanit.simpleweather.features.home.di

import com.achsanit.simpleweather.features.home.domain.IWeatherRepository
import com.achsanit.simpleweather.features.home.domain.WeatherInteractor
import com.achsanit.simpleweather.features.home.domain.WeatherUseCase
import com.achsanit.simpleweather.features.home.ui.ListWeatherAdapter
import com.achsanit.simpleweather.foundation.utils.LocationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideUseCase(repository: IWeatherRepository): WeatherUseCase {
        return WeatherInteractor(repository)
    }

    @Singleton
    @Provides
    fun provideListWeatherAdapter(locationUtil: LocationUtil): ListWeatherAdapter {
        return ListWeatherAdapter(locationUtil)
    }
}