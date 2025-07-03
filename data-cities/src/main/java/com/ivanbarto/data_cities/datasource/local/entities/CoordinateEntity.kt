package com.ivanbarto.data_cities.datasource.local.entities

import com.ivanbarto.data_cities.datasource.remote.dto.CoordinateDto

data class CoordinateEntity(
    val lon: String = "",
    val lat: String = ""
)

fun CoordinateEntity.toDto(): CoordinateDto = CoordinateDto(
    lon = lon,
    lat = lat
)