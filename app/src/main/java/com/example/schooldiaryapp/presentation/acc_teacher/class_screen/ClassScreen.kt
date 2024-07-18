package com.example.schooldiaryapp.presentation.acc_teacher.class_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.schooldiaryapp.R
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.data.network.models.Student
import com.example.schooldiaryapp.presentation.models.StudentListState
import com.example.schooldiaryapp.ui.theme.AppBarBgColor

val TOP_BAR_HEIGHT = 56.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClassScreen(
    students: StudentListState,
    onAddGrade: (grade: Grade) -> Unit
) {
    val feedback = remember { mutableStateOf("") }
    val selectedGrade = remember { mutableStateOf("") }

    Scaffold(
        topBar = {

            TopClassBar()
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(TOP_BAR_HEIGHT))
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
//                    items(students.studentList) { student ->
//                        StudentItem(student, selectedGrade = selectedGrade)
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = feedback.value,
                        onValueChange = { feedback.value = it },
                        label = { Text("Feedback") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(color = Color.Green)
                            .clickable {
//                                val grade = Grade(
//                                    studentId = 1,
//                                    grade = selectedGrade.value.toInt(),
//                                    feedBack = feedback.value
//                                )
//                                onAddGrade(grade)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_send_24),
                            contentDescription = ""
                        )
                    }
                }

            }
        }
    )
}

@Composable
fun TopClassBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppBarBgColor)
            .height(TOP_BAR_HEIGHT),
        title = {

        })
}


@Composable
fun StudentItem(student: Student, selectedGrade: MutableState<String>) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = student.fullname,
            modifier = Modifier.weight(1f)
        )


//        student.gradeList?.forEach { grade ->
//            Text(text = grade.grade.toString(), modifier = Modifier.padding(4.dp))
//        }
        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = selectedGrade.value,
            onValueChange = { selectedGrade.value = it },
            modifier = Modifier.width(60.dp)
        )


    }


}

//
//fun sampleStudents(): List<Student> {
//    return listOf(
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)),
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)),
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)),
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)),
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)),
//        Student(1, "John Doe", listOf(4, 5, 3)),
//        Student(2, "Jane Smith", listOf(5, 4, 4)),
//        Student(3, "Samuel Johnson", listOf(2, 3, 3)
//        ),
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ClassScreen(students = sampleStudents())
//}