package com.example.schooldiaryapp.presentation.acc_student.subject_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.presentation.models.GradeListState


val TOP_BAR_HEIGHT = 56.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubjectDetailScreen(
    grades: GradeListState,
    subject: String?
) {

    val selectedGrade = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopClassBar(subject ?: "Default subject")
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
                    items(grades.gradeList) { grade ->
                        StudentItem(grade, selectedGrade = selectedGrade)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }



            }
        }
    )
}

@Composable
fun TopClassBar(subject: String) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .height(TOP_BAR_HEIGHT),
        title = { Text(subject) })
}

@Composable
fun StudentItem(grade: Grade, selectedGrade: MutableState<String>) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

    ) {
        Text(
            text = grade.grade.toString(),
            fontStyle = FontStyle.Normal,
            modifier = Modifier.fillMaxWidth()
        )
//        Text(
//            text = grade.feedBack,
//            fontStyle = FontStyle.Normal,
//            modifier = Modifier.fillMaxWidth()
//        )




    }


}