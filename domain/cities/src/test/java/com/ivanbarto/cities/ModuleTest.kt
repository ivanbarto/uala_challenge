package com.ivanbarto.cities

import com.ivanbarto.cities.interactors.CityInteractor
import com.ivanbarto.cities.koin.CitiesDomainIsolatedKoinContext
import com.ivanbarto.cities.koin.citiesDomainModule
import com.ivanbarto.cities.repository.CityRepository
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
    override fun getKoin(): Koin = CitiesDomainIsolatedKoinContext.koin

    val interactor: CityInteractor by inject()
    val repository: CityRepository by inject()

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_modules() {
        citiesDomainModule().forEach {
            it.verify()
        }
    }

    @After
    fun stopClient() {
        stopKoin()
    }

    @Test
    fun components_should_exist() {
        assertNotNull(interactor)
        assertNotNull(repository)
    }
}