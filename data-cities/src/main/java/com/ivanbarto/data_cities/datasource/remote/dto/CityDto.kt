package com.ivanbarto.data_cities.datasource.remote.dto

import com.google.gson.annotations.SerializedName
import com.ivanbarto.data_cities.datasource.local.entities.CityEntity
import com.ivanbarto.data_cities.datasource.local.entities.CoordinateEntity

data class CityDto(
    @SerializedName("_id")
    val id: String? = null,

    val country: String? = null,

    val name: String? = null,

    @SerializedName("coord")
    val coordinate: CoordinateDto? = null,

    val savedAsFavourite: Boolean = false
)

fun CityDto.toEntity(): CityEntity = CityEntity(
    id = id.orEmpty(),
    country = country.orEmpty(),
    name = name.orEmpty(),
    coordinate = coordinate?.toEntity() ?: CoordinateEntity(),
    savedAsFavourite = savedAsFavourite
)