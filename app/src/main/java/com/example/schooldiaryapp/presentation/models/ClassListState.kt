package com.example.schooldiaryapp.presentation.models

import com.example.schooldiaryapp.data.network.models.SchoolClass

data class ClassListState(
    var classList: List<SchoolClass> =  listOf(),
    var loadError: String = "",
    var isLoading: Boolean = false,
)