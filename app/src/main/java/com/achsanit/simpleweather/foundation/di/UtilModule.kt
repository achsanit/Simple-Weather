package com.achsanit.simpleweather.foundation.di

import android.content.Context
import com.achsanit.simpleweather.foundation.utils.LocationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    fun provideLocationManager(@ApplicationContext context: Context) =
        LocationUtil(context)

}