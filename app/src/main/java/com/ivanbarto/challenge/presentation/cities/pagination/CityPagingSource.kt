package com.ivanbarto.challenge.presentation.cities.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ivanbarto.domain_cities.City
import com.ivanbarto.domain_cities.CityInteractor

class CityPagingSource(
    private val cityInteractor: CityInteractor,
    private val filterFavorites: Boolean,
    private val prefix: String
) : PagingSource<Int, City>() {
    private var databasePopulated = false

    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return try {
            val page = params.key ?: 1

            if (page == 1 && databasePopulated.not()) {
                cityInteractor.fetchCities()
                databasePopulated = true
            }

            var prevKey: Int? = null
            var nextKey: Int? = null

            val results = when {
                filterFavorites -> {
                    if (prefix.isNotEmpty()) {
                        cityInteractor.favoriteCitiesByPrefix(prefix)
                    } else {
                        cityInteractor.favoriteCities()
                    }
                }

                prefix.isNotEmpty() -> {
                    cityInteractor.citiesByPrefix(prefix)
                }

                else -> {
                    val paginatedCities = cityInteractor.paginatedCities(page = page)

                    prevKey = if (page == 1) null else page.minus(1)
                    nextKey = if (paginatedCities.isEmpty()) null else page.plus(1)

                    paginatedCities
                }
            }

            LoadResult.Page(
                data = results,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}