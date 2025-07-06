package com.ivanbarto.data_cities.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivanbarto.data_cities.datasource.local.Constants
import com.ivanbarto.data_cities.datasource.local.entities.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM ${Constants.CITIES_TABLE} ORDER BY name || country")
    suspend fun getCities(): List<CityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_TABLE} WHERE id == :id")
    suspend fun getCity(id: String): CityEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Update
    suspend fun updateCity(city: CityEntity)
}