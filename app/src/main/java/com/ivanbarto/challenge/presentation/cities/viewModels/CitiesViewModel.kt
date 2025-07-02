package com.ivanbarto.challenge.presentation.cities.viewModels

import androidx.lifecycle.viewModelScope
import com.ivanbarto.challenge.presentation.base.BaseViewModel
import com.ivanbarto.challenge.presentation.base.UiState
import com.ivanbarto.domain.cities.interactors.CityInteractor
import com.ivanbarto.domain.cities.models.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CitiesViewModel(private val cityInteractor: CityInteractor) : BaseViewModel() {

    private val _cityNameFilter: MutableStateFlow<String> = MutableStateFlow("")
    val cityNameFilter: StateFlow<String> = _cityNameFilter.asStateFlow()

    private val _selectedCity: MutableStateFlow<City?> = MutableStateFlow(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()


    val cities: StateFlow<List<City>> = flow {
        setUiState(UiState.LOADING)

        try {
            val result = cityInteractor.cities()
            setUiState(UiState.IDLE)
            emit(result)
        } catch (e: Exception) {
            setUiState(UiState.ERROR)
        }
    }.combine(cityNameFilter) { result, filterValue ->
        result.filter {
            (it.name + it.country).startsWith(
                filterValue,
                ignoreCase = true
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun filterCityByName(name: String) {
        _cityNameFilter.value = name
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }
}