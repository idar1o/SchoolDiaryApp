package com.example.schooldiaryapp.presentation.models

import com.example.schooldiaryapp.data.source.network.models.Teacher

data class SubjectListState(
    var subjectList: List<Teacher> =  listOf(),
    var loadError: String = "",
    var isLoading: Boolean = false,
)