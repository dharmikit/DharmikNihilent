package com.app.dharmiknihilent.database.converters

import androidx.room.TypeConverter
import com.app.dharmiknihilent.models.Price
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RoomConverters {

    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<Price> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Price>>() {}.type
        return gson.fromJson<List<Price>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Price>): String {
        return gson.toJson(someObjects)
    }
}