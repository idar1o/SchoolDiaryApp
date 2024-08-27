package com.example.schooldiaryapp.domain.models

import java.time.LocalDate

data class Assignment(
    val assignmentId: Int,
    val classId: Int,
    val deadline: LocalDate,
    val assignmentDay: LocalDate,
    val description: String,
    val subjectName: String,
    val subjectId: Int,
    val studentsId: List<Int>,
    val title: String,
    val studentNames: List<String>
)
