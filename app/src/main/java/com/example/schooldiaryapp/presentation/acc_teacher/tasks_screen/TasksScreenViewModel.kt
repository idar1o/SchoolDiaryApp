package com.example.schooldiaryapp.presentation.acc_teacher.tasks_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.models.Assignment
import com.example.schooldiaryapp.domain.use_cases.GetAssignmentsByClassIdUseCase
import com.example.schooldiaryapp.utils.Result.Error
import com.example.schooldiaryapp.utils.Result.Loading
import com.example.schooldiaryapp.utils.Result.Success
import com.example.schooldiaryapp.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface TasksListState {
    data class Success(
        val tasksList: List<Assignment>,
        ) : TasksListState

    data class Error(val exception: Throwable? = null) : TasksListState
    data object Loading : TasksListState
}


@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val getAssignmentsByClassIdUseCase: GetAssignmentsByClassIdUseCase
) : ViewModel(){
    private val _classId = MutableStateFlow<Long?>(null) // Хранит текущее значение classId
    val classId: StateFlow<Long?> = _classId

    fun setClassId(id: Long) {
        _classId.value = id
    }

    val uiState: StateFlow<TasksListState> = classId.filterNotNull().flatMapLatest { id ->
        getAssignmentsByClassIdUseCase(id)
            .asResult()
            .map { result ->
                when (result) {
                    is Loading -> {
                        Log.d("LOL", "is Loading")
                        TasksListState.Loading
                    }
                    is Success -> {
                        Log.d("LOL", "is Success ${result.data.get(0).subjectName }")
                        TasksListState.Success(result.data)
                    }
                    is Error -> {
                        Log.d("LOL", "is Error ${result.exception.message }")
                        TasksListState.Error(result.exception)
                    }
                }
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TasksListState.Loading,
    )
}

