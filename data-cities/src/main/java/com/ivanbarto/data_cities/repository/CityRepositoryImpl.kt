package com.ivanbarto.data_cities.repository

import androidx.room.withTransaction
import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.toDto
import com.ivanbarto.data_cities.datasource.remote.CitiesApi
import com.ivanbarto.data_cities.datasource.remote.dto.CityDto
import com.ivanbarto.data_cities.datasource.remote.dto.toPaginatedEntity

private const val PAGE_SIZE = 40

class CityRepositoryImpl(private val citiesApi: CitiesApi,private val dataBase: CityDataBase, private val dao: CityDao) :
    CityRepository {

    override suspend fun citiesPaginated(page: Int): List<CityDto> =
        dao.getPaginatedCities(page).map { it.toDto() }

    override suspend fun getCitiesByPrefix(prefix: String): List<CityDto> =
        dao.getCitiesByPrefix("$prefix%").map { it.toDto() }

    override suspend fun getFavoriteCitiesByPrefix(prefix: String): List<CityDto> =
        dao.getFavoriteCitiesByPrefix("$prefix%").map { it.toDto() }

    override suspend fun getFavoriteCities(): List<CityDto> =
        dao.getFavoriteCities().map { it.toDto() }

    override suspend fun city(id: String): CityDto = dao.getCity(id).toDto()

    override suspend fun fetchPaginatedCities() {
        if (dao.isTableEmpty()){
            val cities = citiesApi.cities()
                .sortedBy { it.name.orEmpty() + it.country.orEmpty() }

            cities.chunked(PAGE_SIZE).forEachIndexed { page, cityPage ->
                dataBase.withTransaction {
                    dao.insertAllPaginated(cityPage.map { it.copy(page = page.inc()).toPaginatedEntity() })
                }
            }
        }
    }

    override suspend fun markCityAsFavorite(cityDto: CityDto) {
        dao.updateCity(cityDto.toPaginatedEntity())
    }
}