package com.ivanbarto.cities.koin

import com.ivanbarto.cities.interactors.CityInteractor
import com.ivanbarto.cities.interactors.CityInteractorImpl
import org.koin.dsl.module

private val modules = module {
    single<CityInteractor> { CityInteractorImpl() }
}

fun citiesDomainModule() = listOf(modules)