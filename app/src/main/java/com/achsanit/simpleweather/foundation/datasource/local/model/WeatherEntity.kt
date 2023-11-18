package com.achsanit.simpleweather.foundation.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "current_time")
    var currentTime: Int = 0,

    @ColumnInfo(name = "icon")
    var icon: String = "",

    @ColumnInfo(name = "location_name")
    var locationName: String = "",

    @ColumnInfo(name = "latitude")
    var lat: Double = 0.0,

    @ColumnInfo(name = "longitude")
    var long: Double = 0.0,

    @ColumnInfo(name = "weather")
    var weather: String = "",

    @ColumnInfo(name = "weather_description")
    var weatherDesc: String = "",

    @ColumnInfo(name = "temperature")
    var temperature: Float = 0f,

    @ColumnInfo(name = "feels_like")
    var feelsLike: Float = 0f,

    @ColumnInfo(name = "humidity")
    var humidity: Int = 0,

    @ColumnInfo(name = "wind_speed")
    var windSpeed: Float = 0f,

    @ColumnInfo(name = "created_at")
    var createdAt: Long? = null,

    @ColumnInfo(name = "updated_at")
    var updatedAt: Long? = null
)
