package com.ivanbarto.domain.cities.interactors

import com.ivanbarto.domain.cities.models.City

interface CityInteractor {
    suspend fun cities(): List<City>
}