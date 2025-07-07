package com.ivanbarto.challenge

import android.content.Context
import com.ivanbarto.challenge.koin.citiesPresentationModules
import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import com.ivanbarto.data_cities.koin.citiesDataModule
import com.ivanbarto.domain_cities.City
import io.mockk.mockk
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PresentationTest : KoinTest {

    private val context = mockk<Context>(relaxed = true)

    @Before
    fun start() {
        startKoin {
            androidContext(context)
            modules(citiesPresentationModules())
        }
    }

    @After
    fun release() {
        stopKoin()
    }


    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_modules() {
        citiesPresentationModules().forEach {
            it.verify()
        }
    }
//
//    @Suppress("LocalVariableName")
//    @Test
//    fun filter_cities_by_prefix_is_correct() {
//        val cities = listOf(
//            City(name = "San Jorge", country = "AR"),
//            City(name = "San Jorge", country = "US"),
//            City(name = "San Jorge", country = "IT"),
//            City(name = "San Jorge", country = "ES"),
//            City(name = "Cordoba", country = "AR"),
//            City(name = "Sacramento", country = "MX"),
//            City(name = "Saratoga", country = "MX"),
//        )
//
//        val validationPrefix1_1 = " san jorge"
//        val validationPrefix1_2 = "sanjor ge"
//        val validationPrefix1_3 = "sanjo rge "
//        val validationPrefix1_4 = "sanjorge"
//        val validationPrefix1_5 = "sanjorgea"
//        val validationPrefix2 = "cordoba"
//        val validationPrefix3 = "sa"
//        val validationPrefix4 = "sac"
//        val validationPrefix5 = "sare"
//
//        assertEquals(
//            4,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix1_1
//            ).size
//        )
//
//        assertEquals(
//            4,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix1_2
//            ).size
//        )
//
//        assertEquals(
//            4,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix1_3
//            ).size
//        )
//
//        assertEquals(
//            4,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix1_4
//            ).size
//        )
//
//        assertEquals(
//            1,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix1_5
//            ).size
//        )
//
//        assertEquals(
//            1,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix2
//            ).size
//        )
//
//        assertEquals(
//            6,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix3
//            ).size
//        )
//
//        assertEquals(
//            1,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix4
//            ).size
//        )
//
//        assertEquals(
//            0,
//            viewModel.filterCities(
//                citiesToFilter = cities,
//                filterValue = validationPrefix5
//            ).size
//        )
//
//    }
}