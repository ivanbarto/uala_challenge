package com.ivanbarto.domain.cities

import com.ivanbarto.domain.cities.interactors.CityInteractor
import com.ivanbarto.domain.cities.koin.CitiesDomainIsolatedKoinContext
import com.ivanbarto.domain.cities.koin.citiesDomainModule
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
    override fun getKoin(): Koin = CitiesDomainIsolatedKoinContext.koin

    private val interactor: CityInteractor by inject()
    private val repository: CityRepository by inject()

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