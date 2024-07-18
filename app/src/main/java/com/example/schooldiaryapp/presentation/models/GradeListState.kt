package com.example.schooldiaryapp.presentation.models

import com.example.schooldiaryapp.data.network.models.Grade

data class GradeListState(
    var gradeList: List<Grade> =  listOf(),
    var loadError: String = "",
    var isLoading: Boolean = false,
)
