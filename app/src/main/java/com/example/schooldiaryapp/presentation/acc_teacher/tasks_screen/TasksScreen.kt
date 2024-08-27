package com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.domain.models.Assignment
import com.example.schooldiaryapp.presentation.components.CircleProgress
import com.example.schooldiaryapp.presentation.components.ErrorContent
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel
import com.example.schooldiaryapp.presentation.navigation.ScreenRoutes

@Composable
fun TasksScreen(navHostController: NavHostController, vm: TasksScreenViewModel, topAppBarViewModel: TopClassesBarViewModel,) {

    val selectedClass by topAppBarViewModel.selectedItem.collectAsState()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect (selectedClass){
        selectedClass?.let{
            selectedClass?.toLong()?.let { it1 -> vm.setClassId(it1) }
        }
        Log.d("LOL", "setClassId is running")
    }

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()){

        TaskMainContent(listState = listState, stateTasksList = uiState, navHostController = navHostController)


    }

}

@Composable
fun TaskMainContent(
    listState: LazyListState,
    stateTasksList: TasksListState,
    navHostController: NavHostController
) {
   when (stateTasksList) {
       is TasksListState.Error -> ErrorContent(exception = stateTasksList.exception)
       is TasksListState.Loading -> CircleProgress(Modifier.fillMaxSize())
       is TasksListState.Success -> {
           LazyColumn(
               modifier = Modifier.padding(top = 16.dp),
               state = listState
           ) {
               items(
                   items = stateTasksList.tasksList,
               ) { task ->
                   TaskItem(task = task){taskId ->
                       navHostController.navigate(ScreenRoutes.TaskItem.createRoute(taskId))
                   }
               }
           }
       }
   }

}


@Composable
fun TaskItem(
    task: Assignment,
    onItemClick: (taskId: Int) -> Unit
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(task.assignmentId) }
        , // Добавлен модификатор clickable,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = task.title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = task.description,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

