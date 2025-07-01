package com.ivanbarto.challenge.presentation.cities

import androidx.lifecycle.viewModelScope
import com.ivanbarto.challenge.presentation.base.BaseViewModel
import com.ivanbarto.challenge.presentation.base.UiState
import com.ivanbarto.domain.cities.interactors.CityInteractor
import com.ivanbarto.domain.cities.models.City
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CitiesViewModel(private val cityInteractor: CityInteractor) : BaseViewModel() {

    val cities: StateFlow<List<City>> = flow {
        setUiState(UiState.LOADING)

        val result = cityInteractor.cities()
        setUiState(UiState.IDLE)

        emit(result)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}