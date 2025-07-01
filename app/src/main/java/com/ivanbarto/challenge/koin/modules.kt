package com.ivanbarto.challenge.koin

import com.ivanbarto.challenge.presentation.cities.CitiesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val modules = module {
    viewModelOf(::CitiesViewModel)
}

fun getCitiesPresentationModules() = listOf(modules)