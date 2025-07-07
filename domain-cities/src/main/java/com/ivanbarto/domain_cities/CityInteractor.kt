package com.ivanbarto.domain_cities

interface CityInteractor {
    suspend fun paginatedCities(page: Int): List<City>
    suspend fun favoriteCities(): List<City>
    suspend fun favoriteCitiesByPrefix(prefix: String): List<City>
    suspend fun citiesByPrefix(prefix: String): List<City>
    suspend fun city(id: String): City
    suspend fun fetchCities()
    suspend fun markCityAsFavorite(city: City)
}