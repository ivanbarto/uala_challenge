package com.ivanbarto.data.cities.repository

import com.ivanbarto.data.cities.datasource.remote.CitiesApi
import com.ivanbarto.data.cities.datasource.remote.dto.CityDto
import com.ivanbarto.data.cities.koin.CitiesDataIsolatedKoinComponent
import com.ivanbarto.data.cities.repository.CityRepository
import org.koin.core.component.inject

class CityRepositoryImpl: CityRepository, CitiesDataIsolatedKoinComponent {
    private val citiesApi: CitiesApi by inject()

    override suspend fun cities(): List<CityDto> {
        return citiesApi.cities()
    }
}