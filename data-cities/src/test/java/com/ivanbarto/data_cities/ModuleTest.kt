package com.ivanbarto.data_cities

import android.content.Context
import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.remote.CitiesApi
import com.ivanbarto.data_cities.koin.citiesDataModule
import com.ivanbarto.data_cities.repository.CityRepository
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify
import kotlin.test.assertNotNull

class ModuleTest : KoinTest {

    private val citiesApi: CitiesApi by inject()
    private val cityRepository: CityRepository by inject()
    private val cityDataBase: CityDataBase by inject()
    private val cityDao: CityDao by inject()

    private val context = mockk<Context>(relaxed = true)

    @Before
    fun start(){
        startKoin {
            androidContext(context)
            modules(citiesDataModule())
        }
    }

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