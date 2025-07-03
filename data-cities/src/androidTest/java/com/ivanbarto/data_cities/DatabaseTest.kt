package com.ivanbarto.data_cities

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.CityEntity
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
        CityEntity(id = "1", name = "City One", country = "Country A"),
        CityEntity(id = "2", name = "City Two", country = "Country B"),
        CityEntity(id = "3", name = "City Three", country = "Country C"),
        CityEntity(id = "4", name = "City Four", country = "Country D")
    )

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        dataBase = provideDataBase(appContext)
        cityDao = provideDao(dataBase)
    }

    @Test
    fun database_register_cities() = runBlocking {
        cityDao.insertAll(entities)

        assert(cityDao.getCities().size == 4)
    }


    @Test
    fun database_marks_city_as_favourite() = runBlocking {
        cityDao.insertAll(entities)

        cityDao.updateCity(entities[2].copy(savedAsFavourite = true))

        assert(cityDao.getCities().filter { it.savedAsFavourite }.size == 1)
    }

    @Test
    fun city_remains_as_favorite_with_database_second_update() = runBlocking {
        cityDao.insertAll(entities)
        assert(cityDao.getCities().size == 4)

        cityDao.updateCity(entities[2].copy(savedAsFavourite = true))
        cityDao.insertAll(entities)

        assert(cityDao.getCities().filter { it.savedAsFavourite }.size == 1)
    }
}