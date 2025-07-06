package com.ivanbarto.challenge.koin

import com.ivanbarto.challenge.presentation.cities.viewModels.CitiesViewModel
import com.ivanbarto.domain_cities.citiesDomainModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val modules = module {
    includes(citiesDomainModule() )
    viewModelOf(::CitiesViewModel)
}

fun citiesPresentationModules() = listOf(modules)