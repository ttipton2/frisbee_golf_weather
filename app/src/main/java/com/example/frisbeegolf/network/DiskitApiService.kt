package com.example.frisbeegolf.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:8080"

private val retrofitBuild = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface DiskitApiService {
    @GET("courses")
    suspend fun getCourses(): List<CourseInfo>
    /*@GET("health")
    suspend fun getHealthCheck(): String*/
}

object DiskitApi {
    val retrofitService : DiskitApiService by lazy {
        retrofitBuild.create(DiskitApiService::class.java)
    }
}