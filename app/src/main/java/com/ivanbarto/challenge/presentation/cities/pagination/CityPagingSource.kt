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
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PaginationConstants.PAGE_INCREMENT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PaginationConstants.PAGE_INCREMENT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return try {
            val page = params.key ?: 1

            if (page == 1 && databasePopulated.not()) {
                cityInteractor.fetchCities()
                databasePopulated = true
            }

            val results = when {
                filterFavorites -> {
                    if (prefix.isNotEmpty()) {
                        cityInteractor.favoriteCitiesByPrefix(page, prefix)
                    } else {
                        cityInteractor.favoriteCities(page)
                    }
                }

                prefix.isNotEmpty() -> {
                    cityInteractor.citiesByPrefix(page, prefix)
                }

                else -> {
                    cityInteractor.paginatedCities(page = page)
                }
            }

            val prevKey =
                if (page == PaginationConstants.FIRST_PAGE) null else page.minus(PaginationConstants.PAGE_INCREMENT)

            val nextKey =
                if (results.isEmpty()) null else page.plus(PaginationConstants.PAGE_INCREMENT)

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