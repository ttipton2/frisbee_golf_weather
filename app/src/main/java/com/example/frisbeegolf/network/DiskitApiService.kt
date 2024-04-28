package com.example.frisbeegolf.network

import com.example.frisbeegolf.model.CourseInfo
import retrofit2.http.GET



interface DiskitApiService {
    @GET("region/VANCOUVER")
    suspend fun getCourses(): List<CourseInfo>//String
    /*@GET("health")
    suspend fun getHealthCheck(): String*/
}