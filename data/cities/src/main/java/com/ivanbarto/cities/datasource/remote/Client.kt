package com.ivanbarto.cities.datasource.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client {
    companion object {
        private const val TIMEOUT = 30L

        private fun okHttpClient(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
        }

        private fun retrofitClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL.BASE)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().create()
                    )
                )
                .client(okHttpClient())
                .build()
        }

        fun citiesApi(): CitiesApi {
            return retrofitClient().create(CitiesApi::class.java)
        }
    }
}