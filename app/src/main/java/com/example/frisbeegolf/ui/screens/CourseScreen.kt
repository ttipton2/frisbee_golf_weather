package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.data.CourseUiState


@Composable
fun CourseScreen(
    courseInfo: CourseUiState,
    modifier: Modifier = Modifier
) {
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
        Text("Icon 1")
        Text("Icon 2")
        Text("Icon 3")
        Text("Icon 4")
    }
}