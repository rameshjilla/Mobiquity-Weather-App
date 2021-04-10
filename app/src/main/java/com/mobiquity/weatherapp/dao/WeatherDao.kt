package com.mobiquity.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    // data has changed.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentweather: CurrentWeatherEntity)


    @Query("SELECT * FROM CurrentWeather WHERE id = :cityId")
    fun getCurrentWeatherFromDb(cityId: Int?): LiveData<CurrentWeatherEntity>


}