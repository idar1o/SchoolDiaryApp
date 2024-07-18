package com.example.schooldiaryapp.presentation.acc_teacher.chat_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.use_cases.GetChatListUseCase
import com.example.schooldiaryapp.domain.use_cases.GetStudentsInfoUseCase
import com.example.schooldiaryapp.presentation.models.ClassListState
import com.example.schooldiaryapp.presentation.models.StudentListState
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val getStudentsInfoUseCase: GetStudentsInfoUseCase
    ) : ViewModel() {

    private val _classList = mutableStateOf(ClassListState())
    val classList: State<ClassListState> = _classList

    private val _studentList = mutableStateOf(StudentListState())
    val studentList: State<StudentListState> = _studentList

    private val _teacherId = 4

    init {
        Log.d("LOL", "В init ViewModel")

        fetchClassList(_teacherId)
        if(_classList.value.classList.isNotEmpty()){
            fetchStudentList(_classList.value.classList[0].classId.toInt())
        }

    }


    fun fetchClassList(teacherId: Int) {
        Log.d("LOL", "В fetchClassList")
        viewModelScope.launch {
            _classList.value = ClassListState(isLoading = true)
            when (val result = getChatListUseCase.invoke(teacherId)) {
                is Resource.Success -> {

                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _classList.value = ClassListState(
                        classList = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    Log.d("LOL", "is Resource.Error -> ${result.data}")
                    _classList.value = ClassListState(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")

                }
            }

            Log.d(
                "LOL", "Error : ${_classList.value.loadError}, " +
                        "Success : ${_classList.value.classList}, " +
                        "Loading : ${_classList.value.isLoading}"
            )
        }
    }

    fun fetchStudentList(classId: Int?) {
        Log.d("LOL", "В fetchStudentList")
        viewModelScope.launch {
            _studentList.value = StudentListState(isLoading = true)
            when (val result = getStudentsInfoUseCase.invoke(classId)) {
                is Resource.Success -> {
                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _studentList.value = StudentListState(
                        studentList = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )

                }

                is Resource.Error -> {
                    Log.d("LOL", "is Resource.Error -> ${result.data}")
                    _studentList.value = StudentListState(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")
                }
            }

            Log.d(
                "LOL", "Error : ${_studentList.value.loadError}, " +
                        "Success : ${_studentList.value.studentList}, " +
                        "Loading : ${_studentList.value.isLoading}"
            )
        }
    }

}
