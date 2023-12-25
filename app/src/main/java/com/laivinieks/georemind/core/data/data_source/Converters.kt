package com.laivinieks.georemind.core.data.data_source

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.laivinieks.georemind.feature_reminder.domain.model.LocationData

class Converters {

    @TypeConverter
    fun fromLocation(location: LocationData?): String? {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String?): LocationData? {
        return Gson().fromJson(locationString, LocationData::class.java)
    }
}