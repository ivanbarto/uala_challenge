package com.ivanbarto.data_cities.repository

import com.ivanbarto.data_cities.datasource.remote.dto.CityDto

interface CityRepository {
    suspend fun citiesPaginated(page: Int): List<CityDto>
    suspend fun getCitiesByPrefix(page: Int, prefix: String): List<CityDto>
    suspend fun getFavoriteCitiesByPrefix(page: Int, prefix: String): List<CityDto>
    suspend fun getFavoriteCities(page: Int): List<CityDto>
    suspend fun city(id: String): CityDto
    suspend fun fetchPaginatedCities()
    suspend fun markCityAsFavorite(cityDto: CityDto)
}