package com.ivanbarto.data_cities.datasource.remote.dto

import com.ivanbarto.data_cities.datasource.local.entities.CoordinateEntity

data class CoordinateDto(
    val lon: String? = null,
    val lat: String? = null
)

fun CoordinateDto.toEntity(): CoordinateEntity = CoordinateEntity(
    lat = lat.orEmpty(),
    lon = lon.orEmpty()
)