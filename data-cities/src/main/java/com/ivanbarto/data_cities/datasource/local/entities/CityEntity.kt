package com.ivanbarto.data_cities.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanbarto.data_cities.datasource.local.Constants
import com.ivanbarto.data_cities.datasource.remote.dto.CityDto


@Entity(tableName = Constants.CITIES_PAGINATED_TABLE)
data class PaginatedCityEntity(
    @PrimaryKey
    val id: String = "",
    val country: String = "",
    val name: String = "",
    val coordinate: CoordinateEntity = CoordinateEntity(),
    val savedAsFavourite: Boolean = false,
    val page: Int
)

@Entity(tableName = Constants.CITIES_PAGINATED_TABLE_FILTER)
data class PaginatedCityEntityFilter(
    @PrimaryKey
    val id: String = "",
    val country: String = "",
    val name: String = "",
    val coordinate: CoordinateEntity = CoordinateEntity(),
    val savedAsFavourite: Boolean = false,
    val page: Int
)

fun PaginatedCityEntity.toDto(): CityDto = CityDto(
    country = country,
    name = name,
    id = id,
    coordinate = coordinate.toDto(),
    savedAsFavourite = savedAsFavourite
)

fun PaginatedCityEntity.toFilter(): PaginatedCityEntityFilter = PaginatedCityEntityFilter(
    country = country,
    name = name,
    id = id,
    coordinate = coordinate,
    savedAsFavourite = savedAsFavourite,
    page = page
)

fun PaginatedCityEntityFilter.toDto(): CityDto = CityDto(
    country = country,
    name = name,
    id = id,
    coordinate = coordinate.toDto(),
    savedAsFavourite = savedAsFavourite
)