package com.ivanbarto.data_cities.koin

import com.ivanbarto.data_cities.datasource.local.CityDataBase
import com.ivanbarto.data_cities.datasource.local.Database
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.remote.CitiesApi
import com.ivanbarto.data_cities.datasource.remote.Client
import com.ivanbarto.data_cities.repository.CityRepository
import com.ivanbarto.data_cities.repository.CityRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val modules = module {
    single<CityDataBase> { Database.provideDataBase(androidContext()) }
    single<CityDao> { Database.provideDao(get()) }
    single<CitiesApi> { Client.citiesApi() }
    single<CityRepository> { CityRepositoryImpl(get(),get(), get()) }
}

fun citiesDataModule() = listOf(modules)