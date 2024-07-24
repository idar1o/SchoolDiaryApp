package com.example.schooldiaryapp.presentation.acc_teacher.grades_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.data.network.models.StudentsInfo
import com.example.schooldiaryapp.presentation.components.TopClassesBarViewModel
import java.time.Duration
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GradeScreen(
    navHostController: NavHostController,
    topAppBarViewModel: TopClassesBarViewModel,
    gradeScreenViewModel: GradeScreenViewModel
) {

    val selectedClass by topAppBarViewModel.selectedItem.collectAsState()
    LaunchedEffect (selectedClass){
        selectedClass?.let{
            gradeScreenViewModel.fetchStudentList(selectedClass?.toInt())
        }

        Log.d("LOL", "fetchStudentList is running")
    }
    val studentList = remember { mutableStateOf<List<StudentsInfo>>(gradeScreenViewModel.studentList.value.studentList) }
    studentList.value = gradeScreenViewModel.studentList.value.studentList

    val listState = rememberLazyListState()


    Box(modifier = Modifier.fillMaxSize()){

        MainContent(listState = listState, stateStudentList = studentList)


    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    listState: LazyListState,
    stateStudentList: MutableState<List<StudentsInfo>>
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
            items = stateStudentList.value,
        ) { student ->
            ClassItem(student = student)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassItem(student: StudentsInfo,
//              onItemClick: (classId: Int) -> Unit
) {
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
                text = student.fullname,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )

            val duration = Duration.between(getLatestGradeDate(student.gradeList), LocalDateTime.now() )
            Text(
                text = "${duration.toDays()} кун мурда",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            ) {
            student.gradeList.forEach { grade ->
                Text(
                    text = grade.grade.toString(),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

    }
}
inline fun getLatestGradeDate(grades: List<Grade>): LocalDateTime? {
    return grades.maxByOrNull { it.gradeDt }?.gradeDt
}