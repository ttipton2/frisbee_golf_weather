package com.example.frisbeegolf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frisbeegolf.network.DiskitApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface DiskitUiState {
    data class Success(val courses: String) : DiskitUiState
    data object Error : DiskitUiState
    data object Loading : DiskitUiState
}


class CourseViewModel : ViewModel() {
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
                val result = DiskitApi.retrofitService.getCourses()
                DiskitUiState.Success("$result")
            } catch (e: IOException) {
                DiskitUiState.Error
            } catch (e: HttpException) {
                DiskitUiState.Error
            }
        }
    }
}