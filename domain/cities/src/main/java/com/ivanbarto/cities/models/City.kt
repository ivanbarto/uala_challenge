package com.ivanbarto.cities.models

import com.ivanbarto.cities.datasource.remote.dto.CityDto

data class City(
    val country: String = "",
    val name: String = "",
    val id: String = "",
    val coordinate: Coordinate = Coordinate()
)

fun CityDto.toDomain(): City = City(
    country = country.orEmpty(),
    name = name.orEmpty(),
    id = id.orEmpty(),
    coordinate = coordinate?.toDomain() ?: Coordinate()
)