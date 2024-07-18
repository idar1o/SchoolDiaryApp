package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName

data class StudentsInfo(
    @SerializedName("student_id")
    var studentId: String,
    @SerializedName("class_id")
    var classId: String,
    @SerializedName("fullname")
    var fullname: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("grades")
    var gradeList: List<Grade>
)
