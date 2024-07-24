package com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen

import com.example.schooldiaryapp.data.network.models.Assignment

data class TasksListState(
    val tasksList: List<Assignment> = listOf(),
    val loadError: String = "",
    val isLoading: Boolean = false
)
