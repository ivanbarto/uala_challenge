package com.ivanbarto.data.cities.koin

import org.koin.dsl.koinApplication

internal object CitiesDataIsolatedKoinContext {
    private val koinApp = koinApplication {
        modules(citiesDataModule())
    }

    val koin = koinApp.koin
}