package com.mobiquity.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import com.mobiquity.weatherapp.model.ForeCast
import com.mobiquity.weatherapp.repo.WeatherRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {


    fun getSavedCurrentWeather(cityId: Int): LiveData<CurrentWeatherEntity> {
        return repository.getCurrentWeather(cityId)
    }

    fun getCurrentweatherFromRemote(
        lat: Double,
        lon: Double
    ): MutableLiveData<CurrentWeatherEntity> {
        return repository.getCurrentWeatherRemote(lat, lon)

    }

    class WeatherViewModelFactory(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(
                    repository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun saveCurrentWeather(currentWeatherEntity: CurrentWeatherEntity) = viewModelScope.launch {
        repository.insertWeather(currentWeatherEntity)
    }

    fun getForecast(lat: Double, lon: Double, apiKey: String): MutableLiveData<String> {
        return repository.getForecastWeather(lat, lon, apiKey)

    }

    fun parseJsonForecast(response: String): List<ForeCast> {
        val jsonArray = JSONObject(response).getJSONArray("list")
        val itemList: MutableList<ForeCast> = ArrayList()
        for (i in 0..jsonArray.length() - 2) {
            val temp = jsonArray.getJSONObject(i).getJSONObject("main").optDouble("temp")
            val humidty = jsonArray.getJSONObject(i).getJSONObject("main").optInt("humidity")
            val cloudsArray = jsonArray.getJSONObject(i).getJSONArray("weather")
            val clouds = cloudsArray.getJSONObject(0).optString("main")
            val cloudsDesc = cloudsArray.getJSONObject(0).optString("description")
            val icon = cloudsArray.getJSONObject(0).optString("icon")
            val speed = jsonArray.getJSONObject(i).getJSONObject("wind").optDouble("speed")
            val fulldate = jsonArray.getJSONObject(i).optString("dt_txt").substring(0, 10)
            val dayeoftheweek = getDayoftheweek(fulldate)
            val foreCast =
                ForeCast(temp, humidty, clouds, cloudsDesc, icon, speed, dayeoftheweek.toString())
            if (!itemList.contains(foreCast)) {
                itemList.add(foreCast)
            }


        }
        return itemList

    }


    private fun compareTwodates(jsondate: Int, currentdate: Int): Boolean {
        if (currentdate > jsondate) {
            return true
        }
        return false

    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("yyyy-mm-dd")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    public fun getDayoftheweek(s: String): String? {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = format1.parse(s)
        return android.text.format.DateFormat.format("EE", dt1).toString()

    }


}