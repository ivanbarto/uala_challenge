package com.ivanbarto.cities.koin

import com.ivanbarto.cities.datasource.remote.CitiesApi
import com.ivanbarto.cities.datasource.remote.Client
import com.ivanbarto.cities.repository.CityRepository
import com.ivanbarto.cities.repository.CityRepositoryImpl
import org.koin.dsl.module

private val modules = module {
    single<CitiesApi> { Client.citiesApi() }
    single<CityRepository> { CityRepositoryImpl() }
}

fun citiesDataModule() = listOf(modules)