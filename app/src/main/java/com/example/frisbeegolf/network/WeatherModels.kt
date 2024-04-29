package com.example.frisbeegolf.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface WeatherServices {
    @GET("weather")
    fun getWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse> // Using Call for simplicity; you might use suspend functions for coroutine support.
}

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val feels_like: Double
)

data class Weather(
    val main: String,
    val description: String
)
