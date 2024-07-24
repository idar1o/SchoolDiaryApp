package com.example.schooldiaryapp.presentation.login

data class TeacherDataState(
    val teacherData: TeacherData? = TeacherData(),
    val loadError: String = "",
    val isLoading: Boolean = false
)

data class TeacherData(
    val username: String = "",
    val password: String = "",
    val fullname: String = "",
    val userId: Int = -1,
    val user_type: String = ""

)

