package com.achsanit.simpleweather.foundation.di

import android.content.Context
import androidx.room.Room
import com.achsanit.simpleweather.foundation.datasource.local.WeatherDao
import com.achsanit.simpleweather.foundation.datasource.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(database: WeatherDatabase): WeatherDao = database.weatherDao()

}