package com.achsanit.simpleweather.foundation.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.achsanit.simpleweather.foundation.datasource.local.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table")
    fun getCurrentWeather(): Flow<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeather(data: WeatherEntity)

    @Query("DELETE FROM weather_table")
    fun deleteWeather()

    @Transaction
    suspend fun deleteAndInsertWeather(data: WeatherEntity) {
        deleteWeather()
        saveCurrentWeather(data)
    }
}