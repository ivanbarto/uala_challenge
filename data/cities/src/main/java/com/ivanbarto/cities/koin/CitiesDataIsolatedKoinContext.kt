package com.ivanbarto.cities.koin

import org.koin.dsl.koinApplication

internal object CitiesDataIsolatedKoinContext {
    private val koinApp = koinApplication {
        modules(citiesDataModule())
    }

    val koin = koinApp.koin
}