package com.ivanbarto.domain.cities.koin

import com.ivanbarto.domain.cities.interactors.CityInteractor
import com.ivanbarto.domain.cities.interactors.CityInteractorImpl
import org.koin.dsl.module

private val modules = module {
    single<CityInteractor> { CityInteractorImpl() }
}

fun citiesDomainModule() = listOf(modules)