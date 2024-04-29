package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.R
import com.example.frisbeegolf.model.CourseInfo
import com.example.frisbeegolf.network.WeatherResponse
import com.example.frisbeegolf.network.WeatherService
import com.example.frisbeegolf.network.WeatherServices


@Composable
fun HomeScreen(
    uiState: DiskitUiState,
    onCourseSelection: (CourseInfo) -> Unit = {},
    weatherService: WeatherService, // Pass the WeatherService instance
    // Pass the API key
    contentPadding: PaddingValues = PaddingValues(16.dp),
    modifier: Modifier = Modifier,
    apiKey: String,
) {
    Column(modifier = modifier) {
        WeatherInputScreen(weatherService, apiKey, modifier)
        when (uiState) {
            is DiskitUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
            is DiskitUiState.Success -> CoursesListScreen(
                uiState.courses,
                onCourseSelection = onCourseSelection,
                contentPadding = contentPadding,
                modifier = modifier.fillMaxWidth()
            )
            is DiskitUiState.Error -> ErrorScreen(modifier.fillMaxSize())
        }
    }
}

@Composable
fun WeatherInputScreen(
    weatherService: WeatherServices,
    apiKey: String,
    modifier: Modifier = Modifier
) {
    var location by remember { mutableStateOf("") }
    var weatherInfo by remember { mutableStateOf("Enter a location to see weather") }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                isLoading = true
                weatherService.getWeather(location, apiKey).enqueue(object : retrofit2.Callback<WeatherResponse> {
                    override fun onResponse(call: retrofit2.Call<WeatherResponse>, response: retrofit2.Response<WeatherResponse>) {
                        isLoading = false
                        if (response.isSuccessful) {
                            val weatherData = response.body()
                            weatherInfo = "Temp: ${weatherData?.main?.temp}°C, Feels like: ${weatherData?.main?.feels_like}°C, Condition: ${weatherData?.weather?.firstOrNull()?.description}"
                        } else {
                            weatherInfo = "Failed to retrieve data"
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<WeatherResponse>, t: Throwable) {
                        isLoading = false
                        weatherInfo = "Error: ${t.message}"
                    }
                })
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (isLoading) "Loading..." else "Check Weather")
        }
        Text(weatherInfo, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.loading_message), modifier = modifier)
    }
}


@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.loading_failed_message), modifier = modifier)
    }
}


@Composable
fun CourseInfoSummary(
    info: CourseInfo,
    onCourseSelection: (CourseInfo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(4.dp, Color.Black)
            .padding(12.dp)
            .clickable{
                onCourseSelection(info)
            }
    ) {
        Text(info.Name)
        Text(info.Address)
        Text(info.City)
        Text(info.State)
        Text("${info.ZipCode}")
    }
}


@Composable
fun CoursesListScreen(
    courses: List<CourseInfo>,
    onCourseSelection: (CourseInfo) -> Unit = {},
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        items(items = courses, key = { course -> course.Id}) {
            course -> CourseInfoSummary(course, onCourseSelection)
        }
    }
}