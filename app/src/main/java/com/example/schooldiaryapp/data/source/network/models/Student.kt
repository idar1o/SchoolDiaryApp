package com.example.schooldiaryapp.data.source.network.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Student @RequiresApi(Build.VERSION_CODES.O) constructor(
    @SerializedName("student_id")
    var studentId: String,
    @SerializedName("class_id")
    var classId: String,
    @SerializedName("fullname")
    var fullname: String,
    @SerializedName("username")
    var username: String,

    var gradeList: MutableState<List<Grade>> = mutableStateOf<List<Grade>>(listOf(
        Grade(1,2, Feedback("",false),1, LocalDateTime.of(2024, 9, 2, 9, 34, 56))
    ))

)
