package com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen

import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.data.network.models.Assignment
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel

@Composable
fun TasksScreen(navHostController: NavHostController, vm: TasksScreenViewModel, topAppBarViewModel: TopClassesBarViewModel,) {

    val selectedClass by topAppBarViewModel.selectedItem.collectAsState()
    LaunchedEffect (selectedClass){
        selectedClass?.let{
            vm.fetchTasksList(selectedClass?.toInt())
        }
        Log.d("LOL", "fetchTasksList is running")
    }

    val tasksList = remember { mutableStateOf(vm.tasksList.value.tasksList) }
    tasksList.value = vm.tasksList.value.tasksList

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()){

        TaskMainContent(listState = listState, stateTasksList = tasksList)


    }

}

@Composable
fun TaskMainContent(
    listState: LazyListState,
    stateTasksList: MutableState<List<Assignment>>
) {
//    val padding by animateDpAsState(
//        targetValue = if (listState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
//        animationSpec = tween(durationMillis = 300)
//    )
    LazyColumn(
        modifier = Modifier.padding(top = 16.dp),
        state = listState
    ) {
        items(
            items = stateTasksList.value,
        ) { task ->
            TaskItem(task = task)
        }
    }
}

@Composable
fun TaskItem(task: Assignment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//            .clickable { onItemClick(schoolClass.classId.toInt()) }, // Добавлен модификатор clickable,
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

