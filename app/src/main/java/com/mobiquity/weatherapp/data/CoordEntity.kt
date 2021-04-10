package com.mobiquity.weatherapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

import com.mobiquity.weatherapp.model.Coord
import kotlinx.android.parcel.Parcelize

/**
 * Created by Furkan on 2019-10-22
 */


@Entity(tableName = "Coord")
data class CoordEntity(
    @ColumnInfo(name = "lon")
    val lon: Double?,
    @ColumnInfo(name = "lat")
    val lat: Double?
)