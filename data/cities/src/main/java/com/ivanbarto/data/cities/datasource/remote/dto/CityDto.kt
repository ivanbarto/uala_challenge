package com.ivanbarto.data.cities.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    val country: String? = null,
    val name: String? = null,
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("coord")
    val coordinate: CoordinateDto? = null
)