package com.ivanbarto.data_cities

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.PaginatedCityEntity
import com.ivanbarto.data_cities.datasource.local.entities.PaginatedCityEntityFilter
import com.ivanbarto.data_cities.datasource.local.entities.toFilter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private fun provideDataBase(context: Context): CityDataBase =
    Room.inMemoryDatabaseBuilder<CityDataBase>(context)
        .build()

private fun provideDao(dataBase: CityDataBase): CityDao = dataBase.getCityDao()

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var dataBase: CityDataBase

    private lateinit var cityDao: CityDao

    private val entities = listOf(
        PaginatedCityEntity(id = "1", name = "City One", country = "CA", page = 1),
        PaginatedCityEntity(id = "2", name = "City Two", country = "AU", page = 1),
        PaginatedCityEntity(id = "3", name = "City Three", country = "US", page = 1),
        PaginatedCityEntity(id = "4", name = "City Four", country = "AR", page = 1)
    )

    private val entitiesWithFavorites = listOf(
        PaginatedCityEntity(id = "1", name = "City One", country = "CA", page = 1),
        PaginatedCityEntity(id = "2", name = "City Two", country = "AU", page = 1, savedAsFavourite = true),
        PaginatedCityEntity(id = "3", name = "City Three", country = "US", page = 1, savedAsFavourite = true),
        PaginatedCityEntity(id = "4", name = "City Four", country = "AR", page = 1)
    )

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        dataBase = provideDataBase(appContext)
        cityDao = provideDao(dataBase)
    }

    @Test
    fun database_register_cities() = runBlocking {
        cityDao.insertAllPaginated(entities)

        assert(cityDao.getPaginatedCities(1).size == 4)
    }


    @Test
    fun database_marks_city_as_favourite() = runBlocking {
        cityDao.insertAllPaginated(entities)

        cityDao.updateCity(entities[2].copy(savedAsFavourite = true))

        assert(cityDao.getPaginatedCities(1).filter { it.savedAsFavourite }.size == 1)
    }

    @Test
    fun city_remains_as_favorite_with_database_second_update() = runBlocking {
        cityDao.insertAllPaginated(entities)
        assert(cityDao.getPaginatedCities(1).size == 4)

        cityDao.updateCity(entities[2].copy(savedAsFavourite = true))
        cityDao.insertAllPaginated(entities)

        assert(cityDao.getPaginatedCities(1).filter { it.savedAsFavourite }.size == 1)
    }

    @Test
    fun cities_are_filtered() = runBlocking {
        cityDao.insertAllPaginated(entities)

        assert(filterByPrefix("city f").size == 1)
        assert(filterByPrefix("city t").size == 2)
        assert(filterByPrefix("city o").size == 1)
        assert(filterByPrefix("city").size == 4)
        assert(filterByPrefix("cityy").isEmpty())
        assert(filterByPrefix("cit y").isEmpty())
        assert(filterByPrefix("city two,c").isEmpty())
        assert(filterByPrefix("city two, ca").isEmpty())
        assert(filterByPrefix("city two, A").size == 1)
        assert(filterByPrefix("city two, a").size == 1)
        assert(filterByPrefix("city two,AU").size == 1)
    }

    @Test
    fun cities_are_filtered_with_favorites() = runBlocking {
        cityDao.insertAllPaginated(entitiesWithFavorites)

        assert(filterFavoriteByPrefix("city f").isEmpty())
        assert(filterFavoriteByPrefix("city t").size == 2)
        assert(filterFavoriteByPrefix("city o").isEmpty())
        assert(filterFavoriteByPrefix("city").size == 2)
        assert(filterFavoriteByPrefix("cityy").isEmpty())
        assert(filterFavoriteByPrefix("cit y").isEmpty())
        assert(filterFavoriteByPrefix("city two,c").isEmpty())
        assert(filterFavoriteByPrefix("city two, ca").isEmpty())
        assert(filterFavoriteByPrefix("city two, A").size == 1)
        assert(filterFavoriteByPrefix("city two, a").size == 1)
        assert(filterFavoriteByPrefix("city two,AU").size == 1)
    }

    private suspend fun filterByPrefix(prefix: String):List<PaginatedCityEntityFilter> {
        val filter = prefix
            .lowercase()
            .replace(", ", "")
            .replace(",", "")

        return cityDao.getCitiesByPrefixFilter("$filter%").map { it.toFilter() }
    }

    private suspend fun filterFavoriteByPrefix(prefix: String):List<PaginatedCityEntityFilter> {
        val filter = prefix
            .lowercase()
            .replace(", ", "")
            .replace(",", "")

        return cityDao.getFavoriteCitiesByPrefixFilter("$filter%").map { it.toFilter() }
    }

}