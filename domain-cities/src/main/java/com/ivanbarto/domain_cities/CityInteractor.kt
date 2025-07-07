package com.ivanbarto.domain_cities

interface CityInteractor {
    suspend fun paginatedCities(page: Int): List<City>
    suspend fun favoriteCities(page: Int): List<City>
    suspend fun favoriteCitiesByPrefix(page: Int, prefix: String): List<City>
    suspend fun citiesByPrefix(page: Int, prefix: String): List<City>
    suspend fun city(id: String): City
    suspend fun fetchCities()
    suspend fun markCityAsFavorite(city: City)
}