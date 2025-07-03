package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.datasource.remote.dto.CoordinateDto

data class Coordinate(
    val lon: String = "",
    val lat: String = ""
)

fun CoordinateDto.toDomain(): Coordinate = Coordinate(
    lon = lon.orEmpty(),
    lat = lat.orEmpty()
)

fun Coordinate.toDto(): CoordinateDto = CoordinateDto(
    lon = lon,
    lat = lat
)