package com.mobiquity.weatherapp.repo

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.mobiquity.utils.ApiInterface
import com.mobiquity.utils.Constants
import com.mobiquity.weatherapp.dao.CityDao
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityRepository(private val cityDao: CityDao) {
    val location = MutableLiveData<String>()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val favCities: Flow<List<City>> = cityDao.getFavCities()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(city: City) {
        cityDao.insertFavCity(city)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFavCity(city: City) {
        cityDao.deleteFavCity(city)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteallCities() {
        cityDao.deleteAllFavoriteCities()
    }

    suspend fun getFavCities() {
        cityDao.getFavCities()
    }

    suspend fun getStaticities() {

    }


    fun getMarkerLocation(lat: Double, long: Double, apiKey: String): MutableLiveData<String> {
        ApiInterface.create().getMarkerLocationfromMap(lat, long, apiKey)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                }

                override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: Response<ResponseBody>?
                ) {
                    if (response?.body() != null)
                        location.postValue(response.body().string())

                }

            })

        return location


    }

    fun getStaticListFromJson(): List<City> {
        val jsonArray = JSONArray(Constants.cities.samplelist)
        val itemList: MutableList<City> = ArrayList()
        for (i in 0..jsonArray.length() - 2) {
            val name = jsonArray.getJSONObject(i).optString("name")
            val lon = jsonArray.getJSONObject(i).optDouble("lon")
            val lat = jsonArray.getJSONObject(i).optDouble("lat")
            val city = City(name, "India", false, lat, lon)
            itemList.add(0, city)

        }
        return itemList
    }


}