package com.example.schooldiaryapp.presentation.login

import com.google.gson.annotations.SerializedName

data class TeacherDataState(
    val teacherData: TeacherData? = null,
    val loadError: String = "",
    val isLoading: Boolean = false
)

data class TeacherData(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("fullname")
    val fullname: String = "",
    @SerializedName("userId")
    val userId: Int = -1,
    @SerializedName("teacherId")
    val teacherId: Int = -1,
    @SerializedName("user_type")
    val user_type: String = ""

)

