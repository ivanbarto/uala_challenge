package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.datasource.remote.dto.CityDto

data class City(
    val country: String = "",
    val name: String = "",
    val id: String = "",
    val coordinate: Coordinate = Coordinate(),
    val savedAsFavourite: Boolean = false
) {
    override fun toString(): String {
        return "$name, $country"
    }
}

fun CityDto.toDomain(): City = City(
    country = country.orEmpty(),
    name = name.orEmpty(),
    id = id.orEmpty(),
    coordinate = coordinate?.toDomain() ?: Coordinate(),
    savedAsFavourite = savedAsFavourite
)

fun City.toDto(): CityDto = CityDto(
    country = country,
    name = name,
    id = id,
    coordinate = coordinate.toDto(),
    savedAsFavourite = savedAsFavourite
)