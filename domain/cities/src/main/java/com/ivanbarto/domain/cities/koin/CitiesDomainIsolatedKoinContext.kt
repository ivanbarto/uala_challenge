package com.ivanbarto.domain.cities.koin

import com.ivanbarto.data.cities.koin.citiesDataModule
import org.koin.dsl.koinApplication

object CitiesDomainIsolatedKoinContext {
    private val koinApp = koinApplication {
        modules(citiesDataModule() + citiesDomainModule())
    }

    val koin = koinApp.koin
}