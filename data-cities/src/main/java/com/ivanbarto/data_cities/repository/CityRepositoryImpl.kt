package com.ivanbarto.data_cities.repository

import androidx.room.withTransaction
import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.PaginatedCityEntityFilter
import com.ivanbarto.data_cities.datasource.local.entities.toDto
import com.ivanbarto.data_cities.datasource.local.entities.toFilter
import com.ivanbarto.data_cities.datasource.remote.CitiesApi
import com.ivanbarto.data_cities.datasource.remote.dto.CityDto
import com.ivanbarto.data_cities.datasource.remote.dto.toPaginatedEntity

private const val PAGE_SIZE = 40

class CityRepositoryImpl(
    private val citiesApi: CitiesApi,
    private val dataBase: CityDataBase,
    private val dao: CityDao
) :
    CityRepository {

    override suspend fun citiesPaginated(page: Int): List<CityDto> =
        dao.getPaginatedCities(page).map { it.toDto() }

    override suspend fun getCitiesByPrefix(page: Int, prefix: String): List<CityDto> {
        val filter = prefix
            .lowercase()
            .replace(", ", "")
            .replace(",", "")

        val results = dao.getCitiesByPrefixFilter("$filter%").map { it.toFilter() }
        clearAndUpdateFilters(results)

        return dao.getCitiesByPrefix(page, "$filter%").map { it.toDto() }
    }

    override suspend fun getFavoriteCitiesByPrefix(page: Int, prefix: String): List<CityDto> {
        val filter = prefix
            .lowercase()
            .replace(", ", "")
            .replace(",", "")

        val results = dao.getFavoriteCitiesByPrefixFilter("$filter%").map { it.toFilter() }
        clearAndUpdateFilters(results)

        return dao.getFavoriteCitiesByPrefix(page, "$filter%").map { it.toDto() }
    }

    override suspend fun getFavoriteCities(page: Int): List<CityDto> {
        val results = dao.getFavoriteCitiesFilter().map { it.toFilter() }
        clearAndUpdateFilters(results)
        return dao.getFavoriteCities(page).map { it.toDto() }
    }

    private suspend fun clearAndUpdateFilters(filters: List<PaginatedCityEntityFilter>) {
        dao.clearAllPaginatedFilter()
        filters.chunked(PAGE_SIZE).forEachIndexed { page, cityPage ->
            dataBase.withTransaction {
                dao.insertAllPaginatedFilter(cityPage.map { it.copy(page = page.inc()) })
            }
        }
    }


    override suspend fun city(id: String): CityDto = dao.getCity(id).toDto()

    override suspend fun fetchPaginatedCities() {
        if (dao.isTableEmpty()) {
            val cities = citiesApi.cities()
                .sortedBy { it.name.orEmpty() + it.country.orEmpty() }

            cities.chunked(PAGE_SIZE).forEachIndexed { page, cityPage ->
                dataBase.withTransaction {
                    dao.insertAllPaginated(cityPage.map {
                        it.copy(page = page.inc()).toPaginatedEntity()
                    })
                }
            }
        }
    }

    override suspend fun markCityAsFavorite(cityDto: CityDto) {
        dao.updateCity(cityDto.toPaginatedEntity())
    }
}