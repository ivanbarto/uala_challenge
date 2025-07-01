package com.ivanbarto.domain.cities.models

import com.ivanbarto.data.cities.datasource.remote.dto.CoordinateDto

data class Coordinate(
    val lon: String = "",
    val lat: String = ""
)

fun CoordinateDto.toDomain(): Coordinate = Coordinate(
    lon = lon.orEmpty(),
    lat = lat.orEmpty()
)