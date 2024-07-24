package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Assignment(
    @SerializedName("assignment_id")
    val assignmentId: Int,
    @SerializedName("class_id")
    val classId: Int,
    @SerializedName("deadline")
    val deadline: LocalDateTime,
    @SerializedName("description")
    val description: String,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("title")
    val title: String
)