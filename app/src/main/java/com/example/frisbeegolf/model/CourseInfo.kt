package com.example.frisbeegolf.model

import kotlinx.serialization.Serializable


@Serializable
data class CourseInfo (
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: Int
)