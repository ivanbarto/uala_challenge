package com.ivanbarto.cities

import com.google.gson.GsonBuilder
import com.ivanbarto.cities.datasource.remote.CitiesApi
import com.ivanbarto.cities.datasource.remote.URL
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.Koin
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClientTest : KoinTest {

    //we declare a custom test module, so we can create a mock web server using MockWebServer()
    //otherwise, we will not be able to use enqueue() to simulate a response
    private val testModules = module {
        single {
            Retrofit.Builder()
                .baseUrl(webServer.url("/"))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().create()
                    )
                )
                .build()
                .create(CitiesApi::class.java)
        }
    }

    //we need an isolated test koin context to use the testModules
    private val isolatedTestKoinContext = koinApplication {
        modules(testModules)
    }
    override fun getKoin(): Koin = isolatedTestKoinContext.koin

    private lateinit var webServer: MockWebServer
    private val citiesApi: CitiesApi by inject()

    @Before
    fun setup() {
        webServer = MockWebServer()
    }

    @After
    fun stopClient() {
        webServer.shutdown()
        stopKoin()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun testMyModules() {
        testModules.verify()
    }

    @Test
    fun load_cities() = runBlocking {
        enqueueResponse()

        val data = citiesApi.cities()

        val request = webServer.takeRequest()

        assert(request.method == "GET")
        assert(request.path == "/" + URL.CITIES)
        assert(data.isNotEmpty())
        assert(data.count() == 114)
    }

    private fun enqueueResponse() {
        webServer.enqueue(MockResponse().setBody(TestUtils.getJsonFromFileAsString("cities.json")))
    }
}