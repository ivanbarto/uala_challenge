package com.ivanbarto.domain.cities.koin

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal interface CitiesDomainIsolatedKoinComponent : KoinComponent {
    override fun getKoin(): Koin = CitiesDomainIsolatedKoinContext.koin
}