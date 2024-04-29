package com.example.frisbeegolf.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
    val apiKey: String = "99086a1b6fa339cd9ee259e9bf60c8f7"
}


