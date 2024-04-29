package com.example.frisbeegolf.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.frisbeegolf.R
import com.example.frisbeegolf.data.CourseUiState
import com.example.frisbeegolf.model.Statuses
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun CourseScreen(
    courseInfo: CourseUiState,
    courseViewModel: CourseViewModel,
    modifier: Modifier = Modifier
) {
    courseViewModel.getCourseStatuses(courseInfo.selectedCourse?.Id)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(4.dp)
    ) {
        Text("${courseInfo.selectedCourse?.Name}")
        //Text("${courseInfo.selectedCourse?.Status?.crowded}")
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = modifier.fillMaxWidth(0.5f)
        ) {
            Text("WEATHER DATA HERE")
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End
        ) {
            when (courseViewModel.courseStatusUiState) {
                is CourseStatusUiState.Loading -> Text("Loading statuses")
                is CourseStatusUiState.Success -> {
                    UpdateStatuses((courseViewModel.courseStatusUiState as CourseStatusUiState.Success).courseStatuses)
                    LaunchedEffect(courseViewModel.courseStatusUiState) {
                        while(true) {
                            delay(5.seconds)
                            courseViewModel.getCourseStatuses(courseInfo.selectedCourse?.Id)
                        }
                    }
                }
                is CourseStatusUiState.Error -> Text("Could not load statuses")
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.padding(32.dp)
    ) {
        Text(
            text = stringResource(R.string.crowded),
            modifier = Modifier
                .border(
                    BorderStroke(width = 4.dp, color = Black),
                    shape = CircleShape
                )
                .clickable {
                    courseViewModel.postCrowded(courseInfo.selectedCourse?.Id)
                }
        )
        Text(
            text = stringResource(R.string.empty),
            modifier = Modifier
                .border(
                    BorderStroke(width = 4.dp, color = Black),
                    shape = CircleShape
                )
                .clickable{
                    courseViewModel.postEmpty(courseInfo.selectedCourse?.Id)
                }
        )
        Text(text = stringResource(R.string.rain),
            modifier = Modifier
                .border(
                    BorderStroke(width = 4.dp, color = Black),
                    shape = CircleShape
                )
                .clickable{
                    courseViewModel.postRain(courseInfo.selectedCourse?.Id)
                }
        )
        Text(text = stringResource(R.string.wind),
            modifier = Modifier
                .border(
                    BorderStroke(width = 4.dp, color = Black),
                    shape = CircleShape
                )
                .clickable{
                    courseViewModel.postWind(courseInfo.selectedCourse?.Id)
                }
        )
    }
}

@Composable
private fun UpdateStatuses(courseStatuses: Statuses) {
    Text("How crowded?: ${courseStatuses.crowded}")
    Text("How empty?: ${courseStatuses.empty}")
    Text("How rainy?: ${courseStatuses.rain}")
    Text("How windy?: ${courseStatuses.wind}")
}
