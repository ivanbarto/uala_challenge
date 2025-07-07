package com.ivanbarto.challenge.koin

import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import com.ivanbarto.challenge.presentation.cities.viewModels.CityDetailsViewModel
import com.ivanbarto.domain_cities.citiesDomainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val modules = module {
    includes(citiesDomainModule())
    viewModel { CityDetailsViewModel(get()) }
    viewModel { CitiesViewModel(get()) }
}

fun citiesPresentationModules() = listOf(modules)