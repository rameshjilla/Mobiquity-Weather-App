package com.mobiquity.weatherapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.mobiquity.weatherapp.model.Clouds
import kotlinx.android.parcel.Parcelize

/**
 * Created by Furkan on 2019-10-24
 */

@Parcelize
@Entity(tableName = "Clouds")
data class CloudsEntity(
    @ColumnInfo(name = "all")
    var all: Int
) : Parcelable {
    @Ignore
    constructor(clouds: Clouds?) : this(
        all = clouds?.all ?: 0
    )
}
