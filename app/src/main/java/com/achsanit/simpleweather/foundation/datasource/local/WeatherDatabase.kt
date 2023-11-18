package com.achsanit.simpleweather.foundation.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achsanit.simpleweather.foundation.datasource.local.model.WeatherEntity


@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}