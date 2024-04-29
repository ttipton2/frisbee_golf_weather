package com.example.frisbeegolf.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.frisbeegolf.CourseInfoApplication
import com.example.frisbeegolf.data.CourseInfoRepository
import com.example.frisbeegolf.data.CourseUiState
import com.example.frisbeegolf.model.CourseInfo
import com.example.frisbeegolf.model.Statuses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface CourseStatusUiState {
    data class Success(val courseStatuses: Statuses) : CourseStatusUiState
    data object Error : CourseStatusUiState
    data object Loading : CourseStatusUiState
}


class CourseViewModel(private val courseInfoRepository: CourseInfoRepository) : ViewModel() {
    private val _courseUiState = MutableStateFlow(CourseUiState())
    var courseUiState: StateFlow<CourseUiState> = _courseUiState.asStateFlow()

    var courseStatusUiState: CourseStatusUiState by mutableStateOf(CourseStatusUiState.Loading)
        private set

    fun setCourse(courseToSelect: CourseInfo) {
        _courseUiState.update { currentState ->
            currentState.copy(
                selectedCourse = courseToSelect
            )
        }
    }


    fun getCourseStatuses(id: String?) {
        viewModelScope.launch {
            //courseStatusUiState = CourseStatusUiState.Loading
            courseStatusUiState = try {
                CourseStatusUiState.Success(courseInfoRepository.getCourseStatuses(id))
            } catch (e: IOException) {
                CourseStatusUiState.Error
            } catch (e: HttpException) {
                CourseStatusUiState.Error
            }
        }
    }


    fun postCrowded(id: String?) {
        viewModelScope.launch {
            try {
                courseInfoRepository.postCrowded(id)
            } catch (e: IOException) {

            } catch (e: HttpException) {

            }
        }
    }

    fun postEmpty(id: String?) {
        viewModelScope.launch {
            try {
                courseInfoRepository.postEmpty(id)
            } catch (e: IOException) {

            } catch (e: HttpException) {

            }
        }
    }

    fun postRain(id: String?) {
        viewModelScope.launch {
            try {
                courseInfoRepository.postRain(id)
            } catch (e: IOException) {

            } catch (e: HttpException) {

            }
        }
    }

    fun postWind(id: String?) {
        viewModelScope.launch {
            try {
                courseInfoRepository.postWind(id)
            } catch (e: IOException) {

            } catch (e: HttpException) {

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CourseInfoApplication)
                val repository = application.container.courseInfoRepository
                CourseViewModel(courseInfoRepository = repository)
            }
        }
    }
}