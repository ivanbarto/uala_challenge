package com.ivanbarto.cities.interactors

import com.ivanbarto.cities.koin.CitiesDomainIsolatedKoinComponent
import com.ivanbarto.cities.models.City
import com.ivanbarto.cities.models.toDomain
import com.ivanbarto.cities.repository.CityRepository
import org.koin.core.component.inject

class CityInteractorImpl : CityInteractor, CitiesDomainIsolatedKoinComponent {
    private val cityRepository: CityRepository by inject()

    override suspend fun cities(): List<City> = cityRepository.cities().map { it.toDomain() }
}