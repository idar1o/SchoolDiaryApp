package com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.use_cases.GetAssignmentsByClassIdUseCase
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val getAssignmentsByClassIdUseCase: GetAssignmentsByClassIdUseCase
) : ViewModel(){
    private val _tasksList = mutableStateOf(TasksListState())
    val tasksList: State<TasksListState> = _tasksList
    fun fetchTasksList(classId: Int?) {
        Log.d("LOL", "В fetchTasksList")
        viewModelScope.launch {
            _tasksList.value = _tasksList.value.copy(isLoading = true)
            when (val result = getAssignmentsByClassIdUseCase.invoke(classId)) {
                is Resource.Success -> {
                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _tasksList.value = _tasksList.value.copy(
                        tasksList = result.data ?: emptyList(),
                        isLoading = false
                    )

                }

                is Resource.Error -> {
                    Log.d("LOL", "is Resource.Error -> ${result.data}")
                    _tasksList.value = _tasksList.value.copy(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")
                }
            }

            Log.d(
                "LOL", "Error : ${_tasksList.value.loadError}, " +
                        "Success : ${_tasksList.value.tasksList}, " +
                        "Loading : ${_tasksList.value.isLoading}"
            )
        }
    }
}

