package com.ivanbarto.cities.interactors

import com.ivanbarto.cities.models.City

interface CityInteractor {
    suspend fun cities(): List<City>
}