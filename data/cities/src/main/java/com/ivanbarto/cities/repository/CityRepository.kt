package com.ivanbarto.cities.repository

import com.ivanbarto.cities.datasource.remote.dto.CityDto

interface CityRepository {
    suspend fun cities(): List<CityDto>
}