package com.example.dashboard.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object RetrofitInstance {
    private const val BASE_URL = "https://imdb236.p.rapidapi.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "4226237bb9msh9dc42d685608170p177cc6jsn54db19933653")
                .addHeader("X-RapidAPI-Host", "imdb236.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: ImdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImdbApi::class.java)
    }
}
