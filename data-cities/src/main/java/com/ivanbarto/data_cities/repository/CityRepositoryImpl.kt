package com.ivanbarto.data_cities.repository

import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.toDto
import com.ivanbarto.data_cities.datasource.remote.CitiesApi
import com.ivanbarto.data_cities.datasource.remote.dto.CityDto
import com.ivanbarto.data_cities.datasource.remote.dto.toEntity

class CityRepositoryImpl(private val citiesApi: CitiesApi, private val dao: CityDao) :
    CityRepository {

    override suspend fun cities(): List<CityDto> = dao.getCities().map { it.toDto() }

    override suspend fun city(id: String): CityDto = dao.getCity(id).toDto()

    override suspend fun fetchCities() {
        val cities = citiesApi.cities()
        dao.insertAll(cities.map { it.toEntity() })
    }

    override suspend fun markCityAsFavorite(cityDto: CityDto) {
        dao.updateCity(cityDto.toEntity())
    }
}