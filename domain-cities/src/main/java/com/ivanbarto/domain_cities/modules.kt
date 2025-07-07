package com.ivanbarto.domain_cities

import com.ivanbarto.data_cities.koin.citiesDataModule
import org.koin.dsl.module

private val modules = module {
    includes(citiesDataModule())
    single<CityInteractor> { CityInteractorImpl(get()) }
}

fun citiesDomainModule() = listOf(modules)