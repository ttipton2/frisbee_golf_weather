package com.example.frisbeegolf.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frisbeegolf.ui.screens.HomeScreen
import com.example.frisbeegolf.ui.screens.CourseViewModel


@Composable
fun DiskitApp() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val courseViewModel: CourseViewModel = viewModel(factory = CourseViewModel.Factory)
        HomeScreen(
            diskitUiState = courseViewModel.diskitUiState
        )
    }
}