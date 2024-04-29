package com.example.frisbeegolf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Statuses (
    @SerialName(value = "Crowded")
    val crowded: Int,
    @SerialName(value = "Empty")
    val empty: Int,
    @SerialName(value = "Rain")
    val rain: Int,
    @SerialName(value = "Wind")
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