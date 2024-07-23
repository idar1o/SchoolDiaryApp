package com.example.schooldiaryapp.presentation.navigation

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schooldiaryapp.R
import com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen.AnnouncementScreen
import com.example.schooldiaryapp.presentation.acc_teacher.grades_screen.GradeScreen
import com.example.schooldiaryapp.presentation.acc_teacher.grades_screen.GradeScreenViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen.ScheduleScreen
import com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen.TasksScreen
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel
import com.example.schooldiaryapp.presentation.login.LoginScreen


@Composable
fun BottomBarNavigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    topAppBarViewModel: TopClassesBarViewModel
) {

    NavHost(
        navController = navHostController, startDestination = ScreenRoutes.Login.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(ScreenRoutes.Login.route) {
            LoginScreen(navHostController = navHostController)
        }
        navigation(
            route = ScreenRoutes.BottomBar.route,
            startDestination = BottomBarRoutes.GRADES.routes
        ) {
            composable(BottomBarRoutes.SCHEDULE.routes) {
                ScheduleScreen(navHostController = navHostController)
            }
            composable(BottomBarRoutes.TASKS.routes) {
                TasksScreen(navHostController = navHostController)
            }
            composable(BottomBarRoutes.GRADES.routes) {
//                ChatListScreen(
//                    onItemClick = {
//
//                    },
//                    navController = navHostController
//                )
                val gradeScreenViewModel : GradeScreenViewModel = hiltViewModel()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    GradeScreen(
                        navHostController = navHostController,
                        topAppBarViewModel = topAppBarViewModel,
                        gradeScreenViewModel = gradeScreenViewModel
                    )
                }
            }
            composable(BottomBarRoutes.ANNOUNCEMENT.routes) {
                AnnouncementScreen(navHostController = navHostController)
            }
        }
    }

}

sealed class ScreenRoutes(val route: String) {

    data object Login : ScreenRoutes("/login")
    data object BottomBar : ScreenRoutes("/bottombar")

}

enum class BottomBarRoutes(
    val id: Int,
    @StringRes val title: Int,
    val routes: String,
    @DrawableRes val icon: Int
) {

    SCHEDULE(1, R.string.schedule, "/schedule", R.drawable.baseline_calendar_month_24),
    TASKS(2, R.string.tasks, "/tasks", R.drawable.baseline_book_24),
    GRADES(3, R.string.grades, "/grades", R.drawable.baseline_looks_5_24),
    ANNOUNCEMENT(3, R.string.announcement, "/announcement", R.drawable.baseline_announcement_24),

}