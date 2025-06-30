package com.ivanbarto.cities.koin

import com.ivanbarto.cities.interactors.CityInteractorImpl
import org.koin.dsl.module

internal val citiesDomainModule = module {
    single { CityInteractorImpl() }
}