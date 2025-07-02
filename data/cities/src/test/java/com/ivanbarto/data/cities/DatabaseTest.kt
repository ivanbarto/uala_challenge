package com.ivanbarto.data.cities

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ivanbarto.data.cities.datasource.local.database.CityDataBase
import com.ivanbarto.data.cities.datasource.local.database.dao.CityDao
import com.ivanbarto.data.cities.datasource.local.database.entities.CityEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.koin.core.Koin
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.inject
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.assertNotNull

class DatabaseTest : KoinTest {

    //we declare a custom test module, so we can create a in-memory database
    //this allow tests to be faster
    private val testModules = module {
        single<CityDataBase> { provideDataBase() }
        single<CityDao> { provideDao(get()) }
    }

    private fun provideDataBase(): CityDataBase =
        Room.inMemoryDatabaseBuilder<CityDataBase>()
            .setDriver(BundledSQLiteDriver())
            .build()

    private fun provideDao(dataBase: CityDataBase): CityDao = dataBase.getCityDao()

    //we need an isolated test koin context to use the testModules
    private val isolatedTestKoinContext = koinApplication {
        modules(testModules)
    }

    override fun getKoin(): Koin = isolatedTestKoinContext.koin

    private val dataBase: CityDataBase by inject()
    private val cityDao: CityDao by inject()

    @After
    fun stop() {
        dataBase.close()
        stopKoin()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun testMyModules() {
        testModules.verify()

        assertNotNull(dataBase)
        assertNotNull(cityDao)
    }

    @Test
    fun database_register_cities() = runBlocking {
        cityDao.insertAllCities(
            listOf(
                CityEntity(id = "1"),
                CityEntity(id = "2"),
                CityEntity(id = "3"),
                CityEntity(id = "4")
            )
        )

        assert(cityDao.getCities().size == 4)
    }

    @Test
    fun database_marks_city_as_favourite() = runBlocking {
        cityDao.insertAllCities(
            listOf(
                CityEntity(id = "1"),
                CityEntity(id = "2"),
                CityEntity(id = "3", savedAsFavourite = true),
                CityEntity(id = "4", savedAsFavourite = true)
            )
        )

        assert(cityDao.getCities().filter { it.savedAsFavourite }.size == 2)
    }
}