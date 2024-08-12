package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class Teacher(
    @SerializedName("teacher_id")
    var teacherId: Int,
    @SerializedName("fullname")
    var fullname: String,
    @SerializedName("subject")
    var subject: String,
)
