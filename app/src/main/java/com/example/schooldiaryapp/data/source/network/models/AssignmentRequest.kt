package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class AssignmentRequest(
    @SerializedName("class_id")
    val classId: Int?,
    @SerializedName("deadline")
    val deadline: String?,
    @SerializedName("assignment_day")
    val assignmentDay: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("subject_name")
    val subjectName: String?,
    @SerializedName("subject_id")
    val subjectId: Int?,
    @SerializedName("teacher_id")
    val teacherId: Int?,
    @SerializedName("students_id")
    val studentsId: List<Int>?,
    @SerializedName("title")
    val title: String?,

)
