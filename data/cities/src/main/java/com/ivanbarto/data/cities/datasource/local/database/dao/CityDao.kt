package com.ivanbarto.data.cities.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivanbarto.data.cities.datasource.local.database.Constants
import com.ivanbarto.data.cities.datasource.local.database.entities.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCities(cities: List<CityEntity>)

    @Query("SELECT * FROM ${Constants.CITIES_TABLE}")
    suspend fun getCities(): List<CityEntity>
}
