package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.repository.CityRepository

class CityInteractorImpl(private val cityRepository: CityRepository) : CityInteractor {

    override suspend fun paginatedCities(page: Int): List<City> =
        cityRepository.citiesPaginated(page = page).map { it.toDomain() }

    override suspend fun favoriteCities(): List<City> =
        cityRepository.getFavoriteCities().map { it.toDomain() }

    override suspend fun favoriteCitiesByPrefix(prefix: String): List<City> =
        cityRepository.getFavoriteCitiesByPrefix(prefix).map { it.toDomain() }

    override suspend fun citiesByPrefix(prefix: String): List<City> =
        cityRepository.getCitiesByPrefix(prefix).map { it.toDomain() }

    override suspend fun city(id: String): City = cityRepository.city(id).toDomain()

    override suspend fun fetchCities() {
        cityRepository.fetchPaginatedCities()
    }

    override suspend fun markCityAsFavorite(city: City) {
        cityRepository.markCityAsFavorite(city.toDto())
    }
}