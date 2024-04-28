package com.example.frisbeegolf.data

import com.example.frisbeegolf.network.DiskitApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val courseInfoRepository: CourseInfoRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8080"

    private val retrofitBuild = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : DiskitApiService by lazy {
        retrofitBuild.create(DiskitApiService::class.java)
    }

    override val courseInfoRepository: CourseInfoRepository by lazy {
        NetworkCourseInfoRepository(retrofitService)
    }
}