package com.ivanbarto.challenge.koin

import android.app.Application
import com.ivanbarto.domain.cities.koin.citiesDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationActivity : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ApplicationActivity)
            modules(
                citiesDomainModule() +
                getCitiesPresentationModules()
            )
        }
    }
}