package com.example.schooldiaryapp.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.schooldiaryapp.R
import com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen.AnnouncementDetailScreen
import com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen.AnnouncementDetailViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen.AnnouncementScreen
import com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen.AnnouncementViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.grade_chat_screen.GradeChatScreen
import com.example.schooldiaryapp.presentation.acc_teacher.grade_chat_screen.GradeChatScreenViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.grades_screen.GradeScreen
import com.example.schooldiaryapp.presentation.acc_teacher.grades_screen.GradeScreenViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen.ScheduleScreen
import com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen.ScheduleScreenViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.TaskItemScreen
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.TaskItemViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen.TasksScreen
import com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen.TasksScreenViewModel
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel
import com.example.schooldiaryapp.presentation.login.LoginScreen
import com.example.schooldiaryapp.presentation.login.LoginScreenViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBarNavigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    topAppBarViewModel: TopClassesBarViewModel,
    context: Context
) {

    NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.Login.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(ScreenRoutes.Login.route) {
            val vm: LoginScreenViewModel = hiltViewModel()
            LoginScreen(navHostController = navHostController, vm = vm, context = context)
        }
        navigation(
            route = ScreenRoutes.BottomBar.route,
            startDestination = BottomBarRoutes.GRADES.routes
        ) {
            composable(BottomBarRoutes.SCHEDULE.routes) {
                val vm: ScheduleScreenViewModel = hiltViewModel()
                ScheduleScreen(navHostController = navHostController, vm = vm)
            }
            composable(BottomBarRoutes.TASKS.routes) {
                val vm: TasksScreenViewModel = hiltViewModel()
                TasksScreen(navHostController = navHostController, vm = vm, topAppBarViewModel = topAppBarViewModel)
            }
            composable(BottomBarRoutes.GRADES.routes) {
                val gradeScreenViewModel: GradeScreenViewModel = hiltViewModel()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    GradeScreen(
                        navHostController = navHostController,
                        topAppBarViewModel = topAppBarViewModel,
                        gradeScreenViewModel = gradeScreenViewModel
                    )
                }
            }
            composable(BottomBarRoutes.ANNOUNCEMENT.routes) {
                val vm: AnnouncementViewModel  = hiltViewModel()
                AnnouncementScreen(vm = vm, navHostController = navHostController)
            }
        }
        // Ensure that the Chat route is directly available in the main graph
        composable(
            route = ScreenRoutes.Chat.route,
            arguments = ScreenRoutes.Chat.args
        ) { backStackEntry ->
            val senderId = backStackEntry.arguments?.getInt("senderId") ?: 0
            val senderName = backStackEntry.arguments?.getString("senderName") ?: ""
            val vm: GradeChatScreenViewModel = hiltViewModel()


            GradeChatScreen(navHostController = navHostController, vm = vm, senderId, senderName = senderName)
        }

        composable(
            route = ScreenRoutes.TaskItem.route,
            arguments = ScreenRoutes.TaskItem.args
        ) { backStackEntry ->
//            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val vm: TaskItemViewModel = hiltViewModel()


            TaskItemScreen(navHostController = navHostController, vm = vm)
        }
        composable(
            route = ScreenRoutes.AnnouncementDetail.route,
            arguments = ScreenRoutes.AnnouncementDetail.args
        ) { backStackEntry ->
            val vm: AnnouncementDetailViewModel = hiltViewModel()
            AnnouncementDetailScreen(navHostController = navHostController, vm = vm)
        }
    }
}

sealed class ScreenRoutes(val route: String, val args: List<NamedNavArgument> = emptyList()) {
    data object Login : ScreenRoutes(route = "/login")
    data object BottomBar : ScreenRoutes(route = "/bottombar")
    data object Chat : ScreenRoutes(
        route = "/chat/{senderId}/{senderName}",
        args = listOf(
            navArgument("senderId") { type = NavType.IntType },
            navArgument("senderName") { type = NavType.StringType },
            )
    ) {
        fun createRoute(senderId: Int, senderName: String) = "/chat/$senderId/$senderName"
    }
    data object TaskItem : ScreenRoutes(
        route = "/taskItem/{taskId}",
        args = listOf(
            navArgument("taskId") { type = NavType.IntType }
        )
    ) {
        fun createRoute(taskId: Int) = "/taskItem/$taskId"
    }

    data object AnnouncementDetail : ScreenRoutes(
        route = "/announcement/{announceId}",
        args = listOf(
            navArgument("announceId") { type = NavType.IntType }
        )
    ) {
        fun createRoute(announceId: Int) = "/announcement/$announceId"
    }
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
    ANNOUNCEMENT(4, R.string.announcement, "/announcement", R.drawable.baseline_announcement_24),
}
