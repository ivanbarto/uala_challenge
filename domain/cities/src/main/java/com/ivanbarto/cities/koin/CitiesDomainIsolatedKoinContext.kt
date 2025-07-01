package com.ivanbarto.cities.koin

import org.koin.dsl.koinApplication

object CitiesDomainIsolatedKoinContext {
    private val koinApp = koinApplication {
        modules(citiesDataModule())
        modules(citiesDomainModule())
    }

    val koin = koinApp.koin
}