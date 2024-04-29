package com.example.frisbeegolf.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.frisbeegolf.R
import com.example.frisbeegolf.ui.screens.HomeScreen
import com.example.frisbeegolf.ui.screens.HomeViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frisbeegolf.ui.screens.CourseScreen
import com.example.frisbeegolf.ui.screens.CourseViewModel
import androidx.navigation.navArgument
import com.example.frisbeegolf.network.RetrofitClient
import com.example.frisbeegolf.network.WeatherService


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiskitApp(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    courseViewModel: CourseViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    weatherService: WeatherService = RetrofitClient.weatherService,
    apiKey: String = RetrofitClient.apiKey,
    contentPadding: PaddingValues = PaddingValues(all = 16.dp),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currScreen = DiskitScreen.valueOf(
        backStackEntry?.destination?.route ?: DiskitScreen.Home.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DiskitTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                currScreen = currScreen,
                navUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPad ->
        val courseUiState by courseViewModel.courseUiState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = DiskitScreen.Home.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPad)
            ) {
                composable(route = DiskitScreen.Home.name) {
                    HomeScreen(
                        uiState = homeViewModel.diskitUiState,
                        onCourseSelection = { courseInfo ->
                            courseViewModel.setCourse(courseInfo)
                            navController.navigate("course/${courseInfo.City}")
                        },
                        weatherService = RetrofitClient.weatherService,
                        apiKey = RetrofitClient.apiKey,
                        contentPadding = PaddingValues(all = 16.dp),
                        modifier = Modifier.fillMaxSize(0F)
                    )
                }

                composable(
                    "course/{cityName}",
                    arguments = listOf(navArgument("cityName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val cityName = backStackEntry.arguments?.getString("cityName") ?: "Unknown"
                    val weatherApiKey = "99086a1b6fa339cd9ee259e9bf60c8f7"
                    CourseScreen(
                        courseInfo = courseViewModel.courseUiState.collectAsState().value,
                        cityName = cityName,
                        weatherApiKey = weatherApiKey,  // Passed securely
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }
    }
}

enum class DiskitScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Course(title = R.string.course)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiskitTopAppBar(
    canNavigateBack: Boolean,
    currScreen: DiskitScreen,
    navUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currScreen.title),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
