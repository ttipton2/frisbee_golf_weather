package com.example.frisbeegolf.ui.screens

import androidx.lifecycle.ViewModel
import com.example.frisbeegolf.data.CourseUiState
import com.example.frisbeegolf.model.CourseInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class CourseViewModel : ViewModel() {
    private val _courseUiState = MutableStateFlow(CourseUiState())
    var courseUiState: StateFlow<CourseUiState> = _courseUiState.asStateFlow()

    fun setCourse(courseToSelect: CourseInfo) {
        _courseUiState.update { currentState ->
            currentState.copy(
                selectedCourse = courseToSelect
            )
        }
    }
}