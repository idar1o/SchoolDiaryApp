package com.example.schooldiaryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schooldiaryapp.presentation.components.TopClassBar
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel
import com.example.schooldiaryapp.presentation.navigation.BottomBarNavigation
import com.example.schooldiaryapp.presentation.navigation.BottomBarRow
import com.example.schooldiaryapp.presentation.navigation.appstate.rememberAppState
import com.example.schooldiaryapp.ui.theme.AppBarBgColor
import com.example.schooldiaryapp.ui.theme.SchoolDiaryAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val topAppBarViewModel: TopClassesBarViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolDiaryAppTheme {

                val appState = rememberAppState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            if (appState.shouldShowBottomBar){
                                TopClassBar(topAppBarViewModel)
                            }
                        },
                        bottomBar = {
                            if (appState.shouldShowBottomBar)
                                BottomAppBar(
                                    containerColor = AppBarBgColor,
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    modifier = Modifier
                                        .height(70.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 24.dp, topEnd = 24.dp
                                            )
                                        )
                                ) {
                                    BottomBarRow(
                                        navHostController = appState.navHostController,
                                    )
                                }
                        },

                    ) { innerPadding ->
                        BottomBarNavigation(
                            navHostController = appState.navHostController,
                            padding = innerPadding,
                            topAppBarViewModel,
                            context = this
                        )
                    }
                }
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


        composable(
            route = "class_tasks_nav_screen/{classId}",
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.IntType
                }
            )
            ) {
//            val viewModel: ChatListViewModel = hiltViewModel()
            val classId = remember {
                it.arguments?.getInt("classId")
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

//            LaunchedEffect(classIdForList) {
//                Log.d("LOL", "classIdForList: $classIdForList")
//                classIdForList?.let { viewModel.fetchStudentList(it) }
//            }
//            val studentInfo = viewModel.studentList.value
//                            produceState<Resource<List<Student>>>(initialValue = Resource.Loading()) {
//                                value = classIdForList?.let { it1 ->
//
//                                }
//                            }.value

        }



    }

}


