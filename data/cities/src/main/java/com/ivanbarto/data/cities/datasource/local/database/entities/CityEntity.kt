package com.ivanbarto.data.cities.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanbarto.data.cities.datasource.local.database.Constants

@Entity(tableName = Constants.CITIES_TABLE)
data class CityEntity(
    @PrimaryKey
    val id: String = "",
    val country: String = "",
    val name: String = "",
    val coordinate: CoordinateEntity = CoordinateEntity(),
    val savedAsFavourite: Boolean = false
)