package com.ivanbarto.cities.models

import com.ivanbarto.cities.datasource.remote.dto.CoordinateDto

data class Coordinate(
    val lon: String = "",
    val lat: String = ""
)

fun CoordinateDto.toDomain(): Coordinate = Coordinate(
    lon = lon.orEmpty(),
    lat = lat.orEmpty()
)