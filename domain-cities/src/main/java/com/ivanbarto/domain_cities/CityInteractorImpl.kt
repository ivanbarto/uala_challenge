package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.repository.CityRepository

class CityInteractorImpl(private val cityRepository: CityRepository) : CityInteractor {

    override suspend fun cities(): List<City> =
        cityRepository.cities().map { it.toDomain() }

    override suspend fun city(id: String): City = cityRepository.city(id).toDomain()

    override suspend fun fetchCities() {
        cityRepository.fetchCities()
    }

    override suspend fun markCityAsFavorite(city: City) {
        cityRepository.markCityAsFavorite(city.toDto())
    }
}