package com.ivanbarto.data.cities

import com.ivanbarto.data.cities.datasource.local.database.CityDataBase
import com.ivanbarto.data.cities.datasource.local.database.dao.CityDao
import com.ivanbarto.data.cities.datasource.remote.CitiesApi
import com.ivanbarto.data.cities.koin.CitiesDataIsolatedKoinContext
import com.ivanbarto.data.cities.koin.citiesDataModule
import com.ivanbarto.data.cities.repository.CityRepository
import org.junit.After
import org.junit.Test
import org.koin.core.Koin
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify
import kotlin.test.assertNotNull

class ModuleTest : KoinTest {
    override fun getKoin(): Koin = CitiesDataIsolatedKoinContext.koin

    private val citiesApi: CitiesApi by inject()
    private val cityRepository: CityRepository by inject()
    private val cityDataBase: CityDataBase by inject()
    private val cityDao: CityDao by inject()

    @After
    fun release() {
        stopKoin()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_modules() {
        citiesDataModule().forEach {
            it.verify()
        }
    }

    @Test
    fun components_should_exist() {
        assertNotNull(citiesApi)
        assertNotNull(cityRepository)
        assertNotNull(cityDataBase)
        assertNotNull(cityDao)
    }

}