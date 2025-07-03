package com.ivanbarto.challenge.presentation.cities.viewModels

import androidx.lifecycle.viewModelScope
import com.ivanbarto.challenge.presentation.base.BaseViewModel
import com.ivanbarto.challenge.presentation.base.UiState
import com.ivanbarto.domain_cities.City
import com.ivanbarto.domain_cities.CityInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CitiesViewModel(private val cityInteractor: CityInteractor) : BaseViewModel() {

    private val _cityNameFilter: MutableStateFlow<String> = MutableStateFlow("")
    val cityNameFilter: StateFlow<String> = _cityNameFilter.asStateFlow()

    private val _filterFavorites: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val filterFavorites: StateFlow<Boolean> = _filterFavorites.asStateFlow()

    private val _selectedCity: MutableStateFlow<City?> = MutableStateFlow(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    private val _cities: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val cities: StateFlow<List<City>> =
        _cities.asStateFlow()
            .combine(cityNameFilter) { citiesToFilter, filterValue ->
                filterCities(citiesToFilter, filterValue)
            }.combine(filterFavorites) { citiesToFilter, shouldFilter ->
                if (shouldFilter) citiesToFilter.filter { it.savedAsFavourite } else citiesToFilter
            }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private fun filterCities(
        citiesToFilter: List<City>,
        filterValue: String
    ) = citiesToFilter.filter {
        (it.name.replace(" ", "") + it.country)
            .startsWith(
                filterValue
                    .replace(",", "")
                    .replace(" ", ""),
                ignoreCase = true
            )
    }

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            try {
                setUiState(UiState.LOADING)

                _cities.value = cityInteractor.cities()
                cityInteractor.fetchCities()
                _cities.value = cityInteractor.cities()

                setUiState(UiState.IDLE)
            } catch (e: Exception) {
                setUiState(UiState.ERROR)
            }
        }
    }

    fun filterCityByName(name: String) {
        _cityNameFilter.value = name
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    fun markAsFavorite(city: City) {
        viewModelScope.launch {
            setUiState(UiState.LOADING)
            cityInteractor.markCityAsFavorite(city.copy(savedAsFavourite = city.savedAsFavourite.not()))

            _cities.value = cityInteractor.cities()
            setUiState(UiState.IDLE)
        }
    }

    fun filterFavorites(shouldFilter: Boolean) {
        _filterFavorites.value = shouldFilter
    }
}