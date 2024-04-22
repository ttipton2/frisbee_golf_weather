package com.example.frisbeegolf.network

import kotlinx.serialization.Serializable


@Serializable
data class CourseInfo (
    val name: String, val address: String
)