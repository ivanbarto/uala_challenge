package com.ivanbarto.domain_cities

import android.content.Context
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.verify.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModuleTest : KoinTest {

    private val context = mockk<Context>(relaxed = true)

    @Before
    fun start() {
        startKoin {
            androidContext(context)
            modules(citiesDomainModule())
        }
    }

    @After
    fun release() {
        stopKoin()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_modules() {
        citiesDomainModule().forEach {
            it.verify()
        }
    }
}