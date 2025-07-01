package com.ivanbarto.domain.cities.interactors

import com.ivanbarto.domain.cities.koin.CitiesDomainIsolatedKoinComponent
import com.ivanbarto.domain.cities.models.City
import com.ivanbarto.domain.cities.models.toDomain
import com.ivanbarto.data.cities.repository.CityRepository
import org.koin.core.component.inject

class CityInteractorImpl : CityInteractor, CitiesDomainIsolatedKoinComponent {
    private val cityRepository: CityRepository by inject()

    override suspend fun cities(): List<City> = cityRepository.cities().map { it.toDomain() }
}