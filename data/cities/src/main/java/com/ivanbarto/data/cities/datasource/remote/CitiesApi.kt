package com.ivanbarto.data.cities.datasource.remote

import com.ivanbarto.data.cities.datasource.remote.dto.CityDto
import retrofit2.http.GET

interface CitiesApi {
    @GET(URL.CITIES)
    suspend fun cities(): List<CityDto>
}