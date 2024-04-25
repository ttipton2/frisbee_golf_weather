package com.example.frisbeegolf.data

import com.example.frisbeegolf.model.CourseInfo
import com.example.frisbeegolf.network.DiskitApiService

interface CourseInfoRepository {
    suspend fun getCourseInfo(): List<CourseInfo>
}


class NetworkCourseInfoRepository(
    private val diskitApiService: DiskitApiService
) : CourseInfoRepository {
    override suspend fun getCourseInfo(): List<CourseInfo> = diskitApiService.getCourses()
}