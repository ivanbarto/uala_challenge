package com.ivanbarto.data.cities.koin

import com.ivanbarto.data.cities.datasource.local.database.CityDataBase
import com.ivanbarto.data.cities.datasource.local.database.Database
import com.ivanbarto.data.cities.datasource.local.database.dao.CityDao
import com.ivanbarto.data.cities.datasource.remote.CitiesApi
import com.ivanbarto.data.cities.datasource.remote.Client
import com.ivanbarto.data.cities.repository.CityRepository
import com.ivanbarto.data.cities.repository.CityRepositoryImpl
import org.koin.dsl.module

private val modules = module {
    single<CityDataBase> { Database.provideDataBase() }
    single<CityDao> { Database.provideDao(get()) }
    single<CitiesApi> { Client.citiesApi() }
    single<CityRepository> { CityRepositoryImpl() }
}

fun citiesDataModule() = listOf(modules)