package com.example.schooldiaryapp.presentation.acc_student.subjects_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.use_cases.GetSubjectsForStudentUseCase
import com.example.schooldiaryapp.presentation.models.SubjectListState
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubjectsListScreenViewModel@Inject constructor(
    private val getSubjectsForStudentUseCase: GetSubjectsForStudentUseCase,

    ) : ViewModel() {
    private val _subjectList = mutableStateOf(SubjectListState())
    val subjectList: State<SubjectListState> = _subjectList
    val _classId = 3

    init {
        Log.d("LOL", "В init SubjectsListScreenViewModel")

        fetchSubjectList(_classId)

    }

    fun fetchSubjectList(classId: Int) {
        Log.d("LOL", "В fetchSubject")
        viewModelScope.launch {
            _subjectList.value = SubjectListState(isLoading = true)
            when (val result = getSubjectsForStudentUseCase.invoke(classId)) {
                is Resource.Success -> {

                    Log.d("LOL", "fetchSubject is Resource.Success -> ${result.data}")
                    _subjectList.value = SubjectListState(
                        subjectList  = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    Log.d("LOL", "fetchSubject is Resource.Error -> ${result.data}")
                    _subjectList.value = SubjectListState(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")

                }
            }

            Log.d("LOL", "Error : ${_subjectList.value.loadError}, " +
                    "Success : ${_subjectList.value.subjectList}, " +
                    "Loading : ${_subjectList.value.isLoading}")
        }
    }


}