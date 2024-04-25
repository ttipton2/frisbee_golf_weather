package com.example.frisbeegolf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.frisbeegolf.CourseInfoApplication
import com.example.frisbeegolf.data.CourseInfoRepository
import com.example.frisbeegolf.data.NetworkCourseInfoRepository
import com.example.frisbeegolf.model.CourseInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface DiskitUiState {
    data class Success(val courses: List<CourseInfo>) : DiskitUiState
    data object Error : DiskitUiState
    data object Loading : DiskitUiState
}


class CourseViewModel(private val courseInfoRepository: CourseInfoRepository) : ViewModel() {
    // Create MutableState class with a default of the Loading state defined above
    // A MutableState class is a single value holder whose reads/writes are observed by compose
    var diskitUiState: DiskitUiState by mutableStateOf(DiskitUiState.Loading)
        private set

    init {
        getCourseList()
    }

    private fun getCourseList() {
        viewModelScope.launch {
            diskitUiState = DiskitUiState.Loading
            diskitUiState = try {
                val courses = courseInfoRepository.getCourseInfo()
                DiskitUiState.Success(courseInfoRepository.getCourseInfo())
            } catch (e: IOException) {
                DiskitUiState.Error
            } catch (e: HttpException) {
                DiskitUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CourseInfoApplication)
                val courseInfoRepository = application.container.courseInfoRepository
                CourseViewModel(courseInfoRepository = courseInfoRepository)
            }
        }
    }
}