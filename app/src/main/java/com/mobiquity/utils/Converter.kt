package com.mobiquity.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.room.TypeConverter
import com.mobiquity.weatherapp.model.WeatherItem

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by Furkan on 2019-10-21
 */

object Converter {


    @TypeConverter
    @JvmStatic
    fun weatherStringToList(data: String?): List<WeatherItem>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, WeatherItem::class.java)
        val adapter = moshi.adapter<List<WeatherItem>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun weatherListToString(objects: List<WeatherItem>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, WeatherItem::class.java)
        val adapter = moshi.adapter<List<WeatherItem>>(type)
        return adapter.toJson(objects)
    }

    fun getTempString(temp: Double): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(humidity: Int): String {
        return humidity.toString() + "°"
    }

    fun getWindString(wind: Double): String {
        return wind.toString() + " km/h"
    }

    @SuppressLint("NewApi")
    fun setWeatherIcon(view: ImageView, iconPath: String?) {
        if (iconPath.isNullOrEmpty())
            return
        val newPath = iconPath.replace(iconPath, "a$iconPath")
        val imageid = view.context.resources.getIdentifier(
            newPath + "_svg",
            "drawable",
            view.context.packageName
        )
        val imageDrawable = view.context.resources.getDrawable(imageid, null)
        view.setImageDrawable(imageDrawable)
    }
}
