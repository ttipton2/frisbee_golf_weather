package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import com.example.frisbeegolf.R


@Composable
fun HomeScreen(
    diskitUiState: DiskitUiState,
    modifier: Modifier = Modifier
) {
    when (diskitUiState) {
        is DiskitUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is DiskitUiState.Success -> SuccessScreen(diskitUiState.courses, modifier.fillMaxWidth())
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