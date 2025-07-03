package com.ivanbarto.challenge.koin

import android.app.Application
import com.ivanbarto.domain_cities.citiesDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationActivity : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(baseContext)
            modules(
                getCitiesPresentationModules()
            )
        }
    }
}