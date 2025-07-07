package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.datasource.remote.dto.CoordinateDto

data class Coordinate(
    val lon: String = "0.0",
    val lat: String = "0.0"
) {
    override fun toString(): String = "$lat, $lon"
}

fun CoordinateDto.toDomain(): Coordinate = Coordinate(
    lon = lon.orEmpty(),
    lat = lat.orEmpty()
)

fun Coordinate.toDto(): CoordinateDto = CoordinateDto(
    lon = lon,
    lat = lat
)