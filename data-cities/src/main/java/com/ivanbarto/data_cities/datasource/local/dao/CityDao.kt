package com.ivanbarto.data_cities.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ivanbarto.data_cities.datasource.local.Constants
import com.ivanbarto.data_cities.datasource.local.entities.PaginatedCityEntity
import com.ivanbarto.data_cities.datasource.local.entities.PaginatedCityEntityFilter

@Dao
interface CityDao {

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE} WHERE page == :page ORDER BY name || country")
    suspend fun getPaginatedCities(page: Int): List<PaginatedCityEntity>

    @Query("SELECT NOT EXISTS(SELECT 1 FROM ${Constants.CITIES_PAGINATED_TABLE})")
    suspend fun isTableEmpty(): Boolean

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE} WHERE id == :id")
    suspend fun getCity(id: String): PaginatedCityEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPaginated(cities: List<PaginatedCityEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPaginatedFilter(cities: List<PaginatedCityEntityFilter>)

    @Query("DELETE FROM ${Constants.CITIES_PAGINATED_TABLE_FILTER}")
    suspend fun clearAllPaginatedFilter()

    @Update
    suspend fun updateCity(city: PaginatedCityEntity)

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE} WHERE savedAsFavourite == 1")
    suspend fun getFavoriteCitiesFilter(): List<PaginatedCityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE} WHERE savedAsFavourite == 1 AND name || country LIKE :prefix")
    suspend fun getFavoriteCitiesByPrefixFilter(prefix: String): List<PaginatedCityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE} WHERE name || country LIKE :prefix")
    suspend fun getCitiesByPrefixFilter(prefix: String): List<PaginatedCityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE_FILTER} WHERE  page == :page AND savedAsFavourite == 1")
    suspend fun getFavoriteCities(page: Int): List<PaginatedCityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE_FILTER} WHERE page == :page AND savedAsFavourite == 1 AND name || country LIKE :prefix")
    suspend fun getFavoriteCitiesByPrefix(page: Int, prefix: String): List<PaginatedCityEntity>

    @Query("SELECT * FROM ${Constants.CITIES_PAGINATED_TABLE_FILTER} WHERE page == :page AND name || country LIKE :prefix")
    suspend fun getCitiesByPrefix(page: Int, prefix: String): List<PaginatedCityEntity>
}