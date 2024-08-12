package com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen

import com.example.schooldiaryapp.data.source.network.models.WeeklySchedules

data class WeeklyScheduleState(
    val weeklySchedules: WeeklySchedules? = null,
    val loadError : String = "",
    val isLoading: Boolean = false
)
