package com.ivanbarto.cities

import com.ivanbarto.cities.koin.citiesDataModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class ModuleTest: KoinTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun testMyModules() {
        citiesDataModule.verify()
    }
}