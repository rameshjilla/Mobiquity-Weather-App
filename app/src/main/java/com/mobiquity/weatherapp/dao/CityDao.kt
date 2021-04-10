package com.mobiquity.weatherapp.dao

import androidx.room.*
import com.mobiquity.weatherapp.data.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavCity(city: City)

    @Query("DELETE FROM cities")
    suspend fun deleteAllFavoriteCities()


    @Delete
    suspend fun deleteFavCity(city: City)

    @Query("SELECT * FROM cities Where isFav=1")
    fun getFavCities(): Flow<List<City>>
}