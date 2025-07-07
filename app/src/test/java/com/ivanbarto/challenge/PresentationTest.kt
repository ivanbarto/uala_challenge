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
}