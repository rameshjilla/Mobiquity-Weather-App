package com.mobiquity.utils

import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/data/2.5/weather")
    fun getCurrentByGeoCords(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String

    ): Call<CurrentWeatherEntity>

    @GET("/geo/1.0/reverse")
    fun getMarkerLocationfromMap(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("appid")
        appId: String
    ): Call<ResponseBody>

    @GET("/data/2.5/forecast")
    fun getforeCast(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String
    ): Call<ResponseBody>

    companion object {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.NetworkService.BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }


}