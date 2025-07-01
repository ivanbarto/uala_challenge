package com.ivanbarto.data.cities.repository

import com.ivanbarto.data.cities.datasource.remote.dto.CityDto

interface CityRepository {
    suspend fun cities(): List<CityDto>
}