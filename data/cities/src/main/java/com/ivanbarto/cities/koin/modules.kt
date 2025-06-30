package com.ivanbarto.cities.koin

import com.ivanbarto.cities.datasource.remote.Client
import com.ivanbarto.cities.repository.CityRepositoryImpl
import org.koin.dsl.module

val citiesDataModule = module {
    single { Client.citiesApi() }
    single { CityRepositoryImpl(get()) }
}