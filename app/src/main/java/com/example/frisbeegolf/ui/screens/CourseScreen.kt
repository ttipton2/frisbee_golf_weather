package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.data.CourseUiState
import com.example.frisbeegolf.network.RetrofitClient

@Composable
fun CourseScreen(
    courseInfo: CourseUiState,
    weatherApiKey: String,
    cityName: String,
    modifier: Modifier = Modifier
) {
    var weather by remember { mutableStateOf("") }

    LaunchedEffect(cityName) {
        try {
            val response = RetrofitClient.weatherService.getWeather(cityName, weatherApiKey)
            weather = "${response.weather.first().main} - ${response.main.temp}°C"
        } catch (e: Exception) {
            weather = "Failed to load weather"
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(4.dp)
    ) {
        Text("${courseInfo.selectedCourse?.Name}")
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.padding(12.dp)
    ) {
        Text("Weather: $weather")
        Text("Icon 1")
        Text("Icon 2")
        Text("Icon 3")
    }
}
