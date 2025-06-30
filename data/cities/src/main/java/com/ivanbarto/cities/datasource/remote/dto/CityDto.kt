package com.ivanbarto.cities.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    val country: String?,
    val name: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("coord")
    val coordinate: CoordinateDto
)