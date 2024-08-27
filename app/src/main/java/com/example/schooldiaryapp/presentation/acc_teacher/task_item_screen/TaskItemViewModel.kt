package com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.di.TeacherId
import com.example.schooldiaryapp.data.source.network.models.AssignmentRequest
import com.example.schooldiaryapp.domain.models.Assignment
import com.example.schooldiaryapp.domain.use_cases.GetAssignmentByIdUseCase
import com.example.schooldiaryapp.domain.use_cases.UpdateAssignmentUseCase
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.navigation.TaskArgs
import com.example.schooldiaryapp.utils.Result.Error
import com.example.schooldiaryapp.utils.Result.Loading
import com.example.schooldiaryapp.utils.Result.Success
import com.example.schooldiaryapp.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed interface TaskItemState{

    data class Success( val task: Assignment ) : TaskItemState

    data class Error(val exception: Throwable? = null) : TaskItemState
    data object Loading : TaskItemState

    data class Init( val task: Assignment ) : TaskItemState
}


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TaskItemViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val updateAssignmentUseCase: UpdateAssignmentUseCase,
    val getAssignmentByIdUseCase: GetAssignmentByIdUseCase,
    @TeacherId private val teacherId: Int
): ViewModel() {

    private val args: TaskArgs by lazy { TaskArgs(savedStateHandle = savedStateHandle) }
    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState : MutableStateFlow<TaskItemState> = MutableStateFlow(
        TaskItemState.Init(
            Assignment(
                assignmentId = 0, classId = 0, LocalDate.MIN, assignmentDay = LocalDate.MIN,
                description = "empty", subjectName = "empty", studentsId = listOf(0),
                title = "empty", studentNames = listOf("empty"), subjectId = 0
            )
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<TaskItemState> = _uiState

    // Состояние обновления (свайп-обновление)
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        // Инициализация данных при создании ViewModel
        refreshAssignment()
    }

    // Метод для обновления данных
    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshAssignment() {
        _isRefreshing.value = true
        viewModelScope.launch {
            getAssignmentByIdUseCase(args.taskId)
                .asResult()
                .map { result ->
                    when (result) {
                        is Loading -> TaskItemState.Loading
                        is Success -> TaskItemState.Success(result.data)
                        is Error -> TaskItemState.Error(result.exception)
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
            _isRefreshing.value = false
        }
    }

    // Метод получения данных и их отображения
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getAssignmentById(taskId: Int): StateFlow<TaskItemState> {
//        return getAssignmentByIdUseCase(taskId)
//            .asResult()
//            .map { result ->
//                when (result) {
//                    is Loading -> TaskItemState.Loading
//                    is Success -> TaskItemState.Success(result.data)
//                    is Error -> TaskItemState.Error(result.exception)
//                }
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = _uiState.value
//            )
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(assignment: Assignment){
        _isRefreshing.value = true
        val formattedDeadline = assignment.deadline?.let { formatDateToRFC3339(it) }
        val formattedAssignmentDay = assignment.assignmentDay?.let { formatDateToRFC3339(it) }

        updateAssignmentUseCase.invoke(
            assignment.assignmentId,
            AssignmentRequest(
                classId = assignment.classId,
                deadline = formattedDeadline,  // Преобразованная дата
                description = assignment.description,
                subjectName = assignment.subjectName,
                teacherId = teacherId,
                studentsId = assignment.studentsId,
                title = assignment.title,
                assignmentDay = formattedAssignmentDay,  // Преобразованная дата
                subjectId = assignment.subjectId
            )).catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
        _isRefreshing.value = false
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateToRFC3339(date: LocalDate): String {
        return date.atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun refreshAssignment() {
//        _isRefreshing.value = true
//        viewModelScope.launch {
//            getAssignmentByIdUseCase(args.taskId)
//                .asResult()
//                .map { result ->
//                    when (result) {
//                        is Loading -> TaskItemState.Loading
//                        is Success -> TaskItemState.Success(result.data)
//                        is Error -> TaskItemState.Error(result.exception)
//                    }
//                }
//                .collect { state ->
//                    _uiState.value = state
//                }
//            _isRefreshing.value = false
//        }
//    }

}