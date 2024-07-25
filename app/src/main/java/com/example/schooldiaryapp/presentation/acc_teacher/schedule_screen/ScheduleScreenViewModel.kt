package com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.di.TeacherId
import com.example.schooldiaryapp.domain.use_cases.GeLessonsByTeacherIDUseCase
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleScreenViewModel @Inject constructor(
    private val getLessonsByTeacherIDUseCase: GeLessonsByTeacherIDUseCase,
    @TeacherId private val teacherId: Int
): ViewModel(){
    private val _scheduleList = mutableStateOf(WeeklyScheduleState())
    val sheduleList: State<WeeklyScheduleState> = _scheduleList

    init {
        fetchWeeklySchedule(teacherId)
    }
    fun fetchWeeklySchedule(teacherId: Int) {
        Log.d("LOL", "В fetchTasksList")
        viewModelScope.launch {
            _scheduleList.value = _scheduleList.value.copy(isLoading = true)
            when (val result = getLessonsByTeacherIDUseCase.invoke(teacherId)) {
                is Resource.Success -> {
                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _scheduleList.value = _scheduleList.value.copy(
                        weeklySchedules = result.data,
                        isLoading = false
                    )

                }

                is Resource.Error -> {
                    Log.d("LOL", "is Resource.Error -> ${result.data}")
                    _scheduleList.value = _scheduleList.value.copy(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")
                }
            }

            Log.d(
                "LOL", "Error : ${_scheduleList.value.loadError}, " +
                        "Success : ${_scheduleList.value.weeklySchedules}, " +
                        "Loading : ${_scheduleList.value.isLoading}"
            )
        }
    }

}