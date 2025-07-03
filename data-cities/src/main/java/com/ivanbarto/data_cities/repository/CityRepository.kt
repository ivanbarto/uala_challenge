package com.ivanbarto.data_cities.repository

import com.ivanbarto.data_cities.datasource.remote.dto.CityDto

interface CityRepository {
    suspend fun cities(): List<CityDto>
    suspend fun fetchCities()
    suspend fun markCityAsFavorite(cityDto: CityDto)
}