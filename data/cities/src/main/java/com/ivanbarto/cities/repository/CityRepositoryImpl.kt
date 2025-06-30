package com.ivanbarto.cities.repository

import com.ivanbarto.cities.datasource.remote.CitiesApi
import com.ivanbarto.cities.datasource.remote.dto.CityDto

class CityRepositoryImpl(private val citiesApi: CitiesApi): CityRepository {
    override suspend fun cities(): List<CityDto> {
        return citiesApi.cities()
    }
}