package com.example.schooldiaryapp.domain.models

import java.time.LocalDate

data class Assignment(
    val assignmentId: Int,
    val classId: Int,
    val deadline: LocalDate,
    val description: String,
    val subjectName: String,
    val studentsId: List<Int>,
    val title: String,
    val studentNames: List<String>
)
