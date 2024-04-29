package com.example.frisbeegolf.model

import kotlinx.serialization.Serializable


@Serializable
data class Statuses (
    val crowded: Int,
    val empty: Int,
    val rain: Int,
    val wind: Int
)


@Serializable
data class CourseInfo (
    val Id: String,
    val Name: String,
    val Address: String,
    val City: String,
    val State: String,
    val ZipCode: Int,
    val Status: Statuses
)