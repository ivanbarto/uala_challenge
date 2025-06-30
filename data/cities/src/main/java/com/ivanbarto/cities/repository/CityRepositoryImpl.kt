package com.ivanbarto.cities.repository

import com.ivanbarto.cities.datasource.remote.CitiesApi
import com.ivanbarto.cities.datasource.remote.dto.CityDto
import com.ivanbarto.cities.koin.CitiesDataIsolatedKoinComponent
import org.koin.core.component.inject

class CityRepositoryImpl: CityRepository, CitiesDataIsolatedKoinComponent {
    private val citiesApi: CitiesApi by inject()

    override suspend fun cities(): List<CityDto> {
        return citiesApi.cities()
    }
}