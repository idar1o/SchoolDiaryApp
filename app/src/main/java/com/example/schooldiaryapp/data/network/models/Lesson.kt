package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("lesson_id") val lessonId: Int,
    @SerializedName("class_name") val className: String,
    @SerializedName("room_num") val roomNum: String,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("subject_name") val subjectName: String,
    @SerializedName("datetimelessons") val datetimeLessons: List<String>
)