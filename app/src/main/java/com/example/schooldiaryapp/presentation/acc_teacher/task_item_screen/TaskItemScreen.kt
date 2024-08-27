package com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.domain.models.Assignment
import com.example.schooldiaryapp.presentation.components.CircleProgress
import com.example.schooldiaryapp.presentation.components.PullToRefreshBox
import com.example.schooldiaryapp.presentation.navigation.BottomBarRoutes
import com.example.schooldiaryapp.ui.theme.AppBarBgColor
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItemScreen(vm: TaskItemViewModel, navHostController: NavHostController){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    TypeTaskContent(uiState = uiState, navHostController = navHostController, vm)
}

@Composable
fun TypeTaskContent(uiState: TaskItemState, navHostController: NavHostController, vm: TaskItemViewModel){
    when(uiState){
        is TaskItemState.Error -> {
            uiState.exception
        }
        is TaskItemState.Loading -> CircleProgress()
        is TaskItemState.Success -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                TaskContent(assignment = uiState.task, navHostController = navHostController, vm)
            }

        }
        is TaskItemState.Init -> {

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    assignment: Assignment,
    navHostController: NavHostController,
    vm: TaskItemViewModel,
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Уроки",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                navHostController.navigate(BottomBarRoutes.TASKS.routes)
                            },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
                backgroundColor = AppBarBgColor
            )
        },
        content = { paddingValues ->

            val isRefreshing by vm.isRefreshing.collectAsStateWithLifecycle()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PullToRefreshBox(
                    item = assignment,
                    content = {assignment ->
                        var title by remember { mutableStateOf(assignment.title) }
                        var description by remember { mutableStateOf(assignment.description) }

                        var dateDialogController by  remember { mutableStateOf(false) }
                        val dateState = rememberDatePickerState()

                        var selectedDeadline by remember {
                            mutableLongStateOf(localDateToMillis(assignment.deadline))
                        }


                            Text(text = "Тема урока", modifier = Modifier.padding(2.dp))
                            ClearableTextField(
                                text = title,
                                onTextChange = { title = it }
                            )
                            ClearableTextField(
                                text = description,
                                onTextChange = { description = it }
                            )
                            Row (modifier = Modifier.fillMaxWidth()) {
                                Button(onClick = { dateDialogController = true }) {
                                    Text(text = "Выберите дату сдачи задания:")
                                }
                                Text(text = convertLongToDate(selectedDeadline))
                            }
                            if (dateDialogController) {
                                DatePickerDialog(
                                    onDismissRequest = { dateDialogController = false },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            if (dateState.selectedDateMillis != null){
                                                selectedDeadline =  dateState.selectedDateMillis!!
                                            }
                                            dateDialogController = false
                                        }) {
                                            Text(text = "Yes")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            dateDialogController = false
                                        }) {
                                            Text(text = "No")
                                        }
                                    }
                                ) {
                                    DatePicker(state = dateState)
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = {
                                val updatedAssignment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    assignment.copy(
                                        title = title,
                                        description = description,
                                        assignmentDay = LocalDate.now(),
                                        deadline =  convertLongToLocalDate(selectedDeadline)
                                        // Обновите другие поля при необходимости
                                    )
                                } else {
                                    TODO("VERSION.SDK_INT < O")
                                }
                                vm.updateData(updatedAssignment)

                            }) {
                                Text(text = "Обновить")
                            }
                        },
                    isRefreshing = isRefreshing,
                    onRefresh = { vm.refreshAssignment() },
                    modifier = Modifier.fillMaxSize().background(Color(0xFFECE5DD))
                    )
            }
        }
    )
}

fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat.getDateInstance()
    return format.format(date)
}
@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToLocalDate(timestamp: Long): LocalDate {
        return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
@RequiresApi(Build.VERSION_CODES.O)
fun localDateToMillis(date: LocalDate): Long {
    val dateTime = date.atStartOfDay() // Конвертируем LocalDate в LocalDateTime с началом дня
    return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() // Конвертируем в миллисекунды
}
@Composable
fun ClearableTextField(
    text: String,
    onTextChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(2.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (text.isNotEmpty()) {
            IconButton(onClick = { onTextChange("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}
