package com.mobiquity.weatherapp.repo

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobiquity.utils.ApiInterface
import com.mobiquity.utils.Constants
import com.mobiquity.weatherapp.dao.CityDao
import com.mobiquity.weatherapp.dao.WeatherDao
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import com.mobiquity.weatherapp.model.ForeCast
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(private val weatherDao: WeatherDao) {
    val lst = MutableLiveData<CurrentWeatherEntity>()
    val forecast = MutableLiveData<String>()
    fun getCurrentWeather(cityId: Int?): LiveData<CurrentWeatherEntity> =
        weatherDao.getCurrentWeatherFromDb(cityId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWeather(currentWeatherEntity: CurrentWeatherEntity) {
        weatherDao.insertCurrentWeather(currentWeatherEntity)
    }


    fun getForecastWeather(lat: Double, long: Double, apiKey: String): MutableLiveData<String> {
        ApiInterface.create().getforeCast(lat, long, Constants.Coords.METRIC, apiKey)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                }

                override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: Response<ResponseBody>?
                ) {
                    if (response?.body() != null)
                        forecast.postValue(response.body().string())

                }

            })

        return forecast


    }


    fun getCurrentWeatherRemote(lat: Double, lon: Double): MutableLiveData<CurrentWeatherEntity> {
        ApiInterface.create().getCurrentByGeoCords(
            lat,
            lon,
            Constants.Coords.METRIC,
            Constants.NetworkService.API_KEY_VALUE
        )
            .enqueue(object : Callback<CurrentWeatherEntity> {
                override fun onFailure(call: Call<CurrentWeatherEntity>?, t: Throwable?) {
                    Log.d("ERROR", t.toString())

                }

                override fun onResponse(
                    call: Call<CurrentWeatherEntity>?,
                    response: Response<CurrentWeatherEntity>?
                ) {
                    if (response?.body() != null)
                        lst.postValue(response.body())


                }

            })
        return lst
    }

}