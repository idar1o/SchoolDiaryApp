package com.example.schooldiaryapp.presentation.models

import com.example.schooldiaryapp.data.network.models.Student

data class StudentListState(
    var studentList: List<Student> =  listOf(),
    var loadError: String = "",
    var isLoading: Boolean = false,
)