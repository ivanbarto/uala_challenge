package com.ivanbarto.challenge.presentation.cities.viewModels

import androidx.lifecycle.viewModelScope
import com.ivanbarto.challenge.presentation.base.BaseViewModel
import com.ivanbarto.domain_cities.City
import com.ivanbarto.domain_cities.CityInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CityDetailsViewModel(private val cityInteractor: CityInteractor) : BaseViewModel() {

    private val _cityDetails: MutableStateFlow<City> = MutableStateFlow(City())
    val cityDetails: StateFlow<City> = _cityDetails.asStateFlow()

    fun getCityDetails(id: String) {
        viewModelScope.launch {
            val city = cityInteractor.city(id)
            _cityDetails.update { city }
        }
    }
}