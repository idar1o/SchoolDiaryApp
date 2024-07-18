package com.example.schooldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schooldiaryapp.presentation.acc_student.subject_detail.SubjectDetailScreen
import com.example.schooldiaryapp.presentation.acc_student.subject_detail.SubjectDetailScreenViewModel
import com.example.schooldiaryapp.presentation.acc_student.subjects_list.SubjectsListScreen
import com.example.schooldiaryapp.presentation.acc_student.subjects_list.SubjectsListScreenViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.chat_list.ChatListScreen
import com.example.schooldiaryapp.presentation.acc_teacher.chat_list.ChatListViewModel
import com.example.schooldiaryapp.presentation.acc_teacher.class_screen.ClassScreen
import com.example.schooldiaryapp.presentation.acc_teacher.class_screen.ClassScreenViewModel
import com.example.schooldiaryapp.presentation.login.LoginScreen
import com.example.schooldiaryapp.ui.theme.SchoolDiaryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolDiaryAppTheme {
//                WebViewScreen()
                Navigation()

            }
        }
    }
}
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login_nav_screen"
    ) {
        Log.d("LOL", "В Navhoste")

        composable(route = "login_nav_screen") {

            LoginScreen(onTeacherClick = {
                navController.navigate(
                    route = "class_list_nav_screen"
                )
            },
                onStudentClick = {

                    navController.navigate(
                        route = "subject_list_nav_screen"
                    )
                }
            )

        }
        composable(route = "subject_list_nav_screen") {
            val viewModel: SubjectsListScreenViewModel = hiltViewModel()
            viewModel.subjectList.value.let { it1 ->
                SubjectsListScreen(
                    stateSubjectList = it1,
                    onItemClick = { teacherId, subject ->

                        navController.navigate(
                            route = "subject_detail_screen/${teacherId}/${subject}"
                        )
                    }
                )
            }
        }
        composable(
            route = "subject_detail_screen/{teacherId}/{subject}",
            arguments = listOf(
                navArgument("teacherId") {
                    type = NavType.IntType
                },
                navArgument("subject") {
                    type = NavType.StringType
                }
            )
        ) {


            val subject = remember {
                it.arguments?.getString("subject")
            }
            val viewModel: SubjectDetailScreenViewModel = hiltViewModel()
            viewModel.gradeList.value.let { it1 ->
                SubjectDetailScreen(
                    subject = subject,
                    grades = it1
                )
            }
        }
        composable(route = "class_list_nav_screen") {
            val viewModel: ChatListViewModel = hiltViewModel()

            viewModel.classList.value.let {it1 ->
                ChatListScreen(
                    stateClassList = it1,
                    onItemClick = {
                    },
                    vm = viewModel
                )
            }




        }

        composable(
            route = "class_detail_screen/{classId}",
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.IntType
                }
            )
        ) {

            val classIdForList = remember {
                it.arguments?.getInt("classId")
            }
            val viewModel: ClassScreenViewModel = hiltViewModel()
//
            LaunchedEffect(classIdForList) {
                Log.d("LOL", "classIdForList: $classIdForList")
                classIdForList?.let { viewModel.fetchStudentList(it) }
            }
            val studentInfo = viewModel.studentList.value
//                            produceState<Resource<List<Student>>>(initialValue = Resource.Loading()) {
//                                value = classIdForList?.let { it1 ->
//
//                                }
//                            }.value
//
            ClassScreen(
                studentInfo,
                onAddGrade = { grade ->
                    viewModel.addGrade(grade)

                }
            )
        }



    }

}


