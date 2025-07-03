package com.ivanbarto.data_cities.datasource.local.entities

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCoordinateDto(coordinateEntityJson: String): CoordinateEntity {
        return gson.fromJson(coordinateEntityJson, CoordinateEntity::class.java)
    }

    @TypeConverter
    fun toCoordinateDto(coordinateEntity: CoordinateEntity): String {
        return gson.toJson(coordinateEntity)
    }
}