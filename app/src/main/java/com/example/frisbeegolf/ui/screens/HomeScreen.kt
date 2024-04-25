package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.R
import com.example.frisbeegolf.model.CourseInfo


@Composable
fun HomeScreen(
    diskitUiState: DiskitUiState,
    modifier: Modifier = Modifier
) {
    when (diskitUiState) {
        is DiskitUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is DiskitUiState.Success -> CoursesListScreen(diskitUiState.courses, modifier)//SuccessScreen(diskitUiState.courses, modifier.fillMaxWidth())
        is DiskitUiState.Error -> ErrorScreen(modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.loading_message), modifier = modifier)
}


@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.loading_failed_message), modifier = modifier)
}

@Composable
fun SuccessScreen(courses: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = courses)
    }
}


@Composable
fun CourseInfoSummary(info: CourseInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .border(4.dp, Color.Yellow)
            .padding(12.dp)
    ) {
        Text(info.name)
        Text(info.address)
        Text(info.city)
        Text(info.state)
        Text("${info.zip}")
    }
}


@Composable
fun CoursesListScreen(
    courses: List<CourseInfo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        items(items = courses, key = { course -> course.address}) {
            course -> CourseInfoSummary(course)
        }
    }
}