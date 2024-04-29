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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.R
import com.example.frisbeegolf.model.CourseInfo


@Composable
fun HomeScreen(
    diskitUiState: DiskitUiState,
    onCourseSelection: (CourseInfo) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(16.dp),
    modifier: Modifier = Modifier,
) {
    when (diskitUiState) {
        is DiskitUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is DiskitUiState.Success -> CoursesListScreen(
            diskitUiState.courses,
            onCourseSelection = onCourseSelection,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is DiskitUiState.Error -> ErrorScreen(modifier.fillMaxSize())
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