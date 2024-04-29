package com.example.frisbeegolf.data

import com.example.frisbeegolf.model.CourseInfo
import com.example.frisbeegolf.model.Statuses
import com.example.frisbeegolf.network.DiskitApiService


interface CourseInfoRepository {
    suspend fun getCourseInfo(): List<CourseInfo>
    suspend fun getCourseStatuses(id: String?): Statuses
    suspend fun postCrowded(id: String?)
    suspend fun postEmpty(id: String?)
    suspend fun postRain(id: String?)
    suspend fun postWind(id: String?)
}


class NetworkCourseInfoRepository(
    private val diskitApiService: DiskitApiService
) : CourseInfoRepository {
    override suspend fun getCourseInfo(): List<CourseInfo> = diskitApiService.getCourses()
    override suspend fun getCourseStatuses(id: String?): Statuses = diskitApiService.getCourseStatuses(id)
    override suspend fun postCrowded(id: String?) = diskitApiService.postCrowded(id)
    override suspend fun postEmpty(id: String?) = diskitApiService.postEmpty(id)
    override suspend fun postRain(id: String?) = diskitApiService.postRain(id)
    override suspend fun postWind(id: String?) = diskitApiService.postWind(id)
}