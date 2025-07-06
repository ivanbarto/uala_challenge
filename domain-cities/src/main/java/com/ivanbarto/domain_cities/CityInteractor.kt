package com.ivanbarto.domain_cities

interface CityInteractor {
    suspend fun cities(): List<City>
    suspend fun city(id: String): City
    suspend fun fetchCities()
    suspend fun markCityAsFavorite(city: City)
}