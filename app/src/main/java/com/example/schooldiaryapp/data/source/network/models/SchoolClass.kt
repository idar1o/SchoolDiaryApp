package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class SchoolClass(
    @SerializedName("class_id")
    var classId: String,
    @SerializedName("class_name")
    var className: String,
    @SerializedName("teacher_id")
    var teacherId: String
)
