package com.example.frisbeegolf.network

import com.example.frisbeegolf.model.CourseInfo
import com.example.frisbeegolf.model.Statuses
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface DiskitApiService {
    @GET("region/VANCOUVER")
    suspend fun getCourses(): List<CourseInfo>


    @GET("status/{id}")
    suspend fun getCourseStatuses(
        @Path("id") id: String?
    ): Statuses

    @POST("crowded/{id}")
    suspend fun postCrowded(
        @Path("id") id: String?
    )

    @POST("empty/{id}")
    suspend fun postEmpty(
        @Path("id") id: String?
    )

    @POST("rain/{id}")
    suspend fun postRain(
        @Path("id") id: String?
    )

    @POST("wind/{id}")
    suspend fun postWind(
        @Path("id") id: String?
    )
}