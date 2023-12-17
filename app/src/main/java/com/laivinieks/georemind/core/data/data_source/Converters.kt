package com.laivinieks.georemind.core.data.data_source

import android.location.Location
import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String?): Location? {
        return Gson().fromJson(locationString, Location::class.java)
    }
}