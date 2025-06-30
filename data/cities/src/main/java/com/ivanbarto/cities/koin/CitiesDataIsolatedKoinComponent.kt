package com.ivanbarto.cities.koin

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal interface CitiesDataIsolatedKoinComponent : KoinComponent {
    override fun getKoin(): Koin = CitiesDataIsolatedKoinContext.koin
}