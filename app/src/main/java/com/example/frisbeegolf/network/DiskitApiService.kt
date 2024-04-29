package com.example.frisbeegolf.network

import com.example.frisbeegolf.model.CourseInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface DiskitApiService {
    @GET("region/VANCOUVER")
    suspend fun getCourses(): List<CourseInfo>//String
    /*@GET("health")
    suspend fun getHealthCheck(): String*/
}

interface WeatherService : WeatherServices {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    override fun getWeather(
        location: String,
        apiKey: String,
        units: String
    ): Call<WeatherResponse> {
        TODO("Not yet implemented")
    }
}
