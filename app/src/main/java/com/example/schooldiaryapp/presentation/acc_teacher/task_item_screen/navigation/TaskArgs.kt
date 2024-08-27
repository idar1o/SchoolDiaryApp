package com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.navigation

import androidx.lifecycle.SavedStateHandle

const val ARG_TASK_ID = "taskId"
data class TaskArgs(
    val taskId: Int
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        taskId = savedStateHandle.get<Int>(ARG_TASK_ID) ?: 0
    )
}