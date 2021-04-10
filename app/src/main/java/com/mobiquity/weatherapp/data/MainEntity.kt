package com.mobiquity.weatherapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.mobiquity.weatherapp.model.Main
import kotlinx.android.parcel.Parcelize

/**
 * Created by Furkan on 2019-10-24
 */


@Entity(tableName = "Main")
data class MainEntity(
    @ColumnInfo(name = "temp")
    var temp: Double?,
    @ColumnInfo(name = "tempMin")
    var tempMin: Double?,
    @ColumnInfo(name = "grndLevel")
    var grndLevel: Double?,
    @ColumnInfo(name = "tempKf")
    var tempKf: Double?,
    @ColumnInfo(name = "humidity")
    var humidity: Int?,
    @ColumnInfo(name = "pressure")
    var pressure: Double?,
    @ColumnInfo(name = "seaLevel")
    var seaLevel: Double?,
    @ColumnInfo(name = "tempMax")
    var tempMax: Double?
)



