package com.example.frisbeegolf.ui

import androidx.annotation.StringRes
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.frisbeegolf.R
import com.example.frisbeegolf.ui.screens.HomeScreen
import com.example.frisbeegolf.ui.screens.DiskitViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frisbeegolf.ui.screens.CourseScreen
import com.example.frisbeegolf.ui.screens.CourseViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiskitApp(
    diskitViewModel: DiskitViewModel = viewModel(factory = DiskitViewModel.Factory),
    courseViewModel: CourseViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = DiskitScreen.valueOf(
        backStackEntry?.destination?.route ?: DiskitScreen.Home.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DiskitTopAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val courseUiState by courseViewModel.courseUiState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = DiskitScreen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = DiskitScreen.Home.name) {
                    HomeScreen(
                        diskitUiState = diskitViewModel.diskitUiState,
                        onCourseSelection = {
                            courseViewModel.setCourse(it)
                            navController.navigate(DiskitScreen.Course.name) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = DiskitScreen.Course.name) {
                    CourseScreen(courseUiState)
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
    currentScreen: DiskitScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall,
                )
            },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
