package com.example.schooldiaryapp.presentation.models

import com.example.schooldiaryapp.data.source.network.models.StudentsInfo

data class StudentListState(
    var studentList: List<StudentsInfo> = listOf(),
    var loadError: String = "",
    var isLoading: Boolean = false,
)