package com.ivanbarto.data.cities.datasource.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ivanbarto.data.cities.datasource.local.database.entities.CoordinateEntity

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