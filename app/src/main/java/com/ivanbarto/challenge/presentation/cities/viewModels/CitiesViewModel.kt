package com.ivanbarto.challenge.presentation.cities.viewModels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.ivanbarto.challenge.presentation.base.BaseViewModel
import com.ivanbarto.challenge.presentation.base.UiState
import com.ivanbarto.challenge.presentation.cities.pagination.CityPagingSource
import com.ivanbarto.challenge.presentation.cities.pagination.PaginationConstants
import com.ivanbarto.domain_cities.City
import com.ivanbarto.domain_cities.CityInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class CitiesViewModel(private val cityInteractor: CityInteractor) : BaseViewModel() {

    private val _cityNameFilter: MutableStateFlow<String> = MutableStateFlow("")
    val cityNameFilter: StateFlow<String> = _cityNameFilter.asStateFlow()

    private val _filterFavorites: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val filterFavorites: StateFlow<Boolean> = _filterFavorites.asStateFlow()

    private val _selectedCity: MutableStateFlow<City?> = MutableStateFlow(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    val cities = Pager(
        config = PagingConfig(
            pageSize = PaginationConstants.PAGE_SIZE,
        ),
        pagingSourceFactory = {
            CityPagingSource(
                cityInteractor = cityInteractor,
                filterFavorites = filterFavorites.value,
                prefix = cityNameFilter.value
            )
        }
    ).flow
        .cachedIn(viewModelScope)
        .combine(cityInteractor.favoriteCities()) { pagingFlow, favoritesFlow ->
            pagingFlow.map { city ->
                city.copy(
                    savedAsFavourite =
                        favoritesFlow.firstOrNull { it.id == city.id } != null
                )
            }
        }
        .catch {
            setUiState(UiState.ERROR)
        }
        .cachedIn(viewModelScope)

    fun filterCityByName(name: String) {
        _cityNameFilter.value = name
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    fun markAsFavorite(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            setUiState(UiState.LOADING)
            cityInteractor.markCityAsFavorite(city.copy(savedAsFavourite = city.savedAsFavourite.not()))

            setUiState(UiState.IDLE)
        }
    }

    fun filterFavorites(shouldFilter: Boolean) {
        _filterFavorites.value = shouldFilter
    }
}