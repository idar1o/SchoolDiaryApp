package com.example.schooldiaryapp.presentation.acc_student.subject_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.use_cases.GetGradesByStudentUseCase
import com.example.schooldiaryapp.presentation.models.GradeListState
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectDetailScreenViewModel @Inject constructor(
    private val getGradesByStudentUseCase: GetGradesByStudentUseCase,

    ) : ViewModel() {
    private val _gradeList = mutableStateOf(GradeListState())
    val gradeList: State<GradeListState> = _gradeList
    val _studentId = 1


    init {
        Log.d("LOL", "В init SubjectDetailScreenViewModel")

        fetchGradeList(_studentId)

    }

    private fun fetchGradeList(_studentId: Int) {

        Log.d("LOL", "В fetchGradeList")
        viewModelScope.launch {
            _gradeList.value = GradeListState(isLoading = true)
            when (val result = getGradesByStudentUseCase.invoke(_studentId)) {
                is Resource.Success -> {

                    Log.d("LOL", "fetchGradeList is Resource.Success -> ${result.data}")
                    _gradeList.value = GradeListState(
                        gradeList  = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    Log.d("LOL", "fetchGradeList is Resource.Error -> ${result.data}")
                    _gradeList.value = GradeListState(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")

                }
            }

            Log.d("LOL", "Error : ${_gradeList.value.loadError}, " +
                    "Success : ${_gradeList.value.gradeList}, " +
                    "Loading : ${_gradeList.value.isLoading}")
        }

    }
}
