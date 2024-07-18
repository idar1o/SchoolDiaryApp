package com.example.schooldiaryapp.presentation.acc_teacher.class_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.domain.use_cases.GetGradesByStudentUseCase
import com.example.schooldiaryapp.domain.use_cases.GetStudentListUseCase
import com.example.schooldiaryapp.domain.use_cases.GradeTheStudentUseCase
import com.example.schooldiaryapp.presentation.models.StudentListState
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClassScreenViewModel@Inject constructor(
    private val getStudentListUseCase: GetStudentListUseCase,
    private val getGradesByStudentUseCase: GetGradesByStudentUseCase,
    private val gradeTheStudent: GradeTheStudentUseCase

) : ViewModel(){
    private val _studentList = mutableStateOf(StudentListState())
    val studentList: State<StudentListState> = _studentList



    init {
        Log.d("LOL", "В init StudentViewModel")

    }

    fun addGrade(grade: Grade) {
        viewModelScope.launch {
            when(val result = gradeTheStudent(grade)){
                is Resource.Success ->{
                    Log.d("LOL", "addGrade is Resource.Success -> ${result.data}")
                }

                is Resource.Error -> {
                    Log.d("LOL", "addGrade is Resource.Error -> ${result.data}${result.message}")
                }
                is Resource.Loading -> {

                }

            }
            // Обработка результата (например, обновление UI или показ сообщения)
        }
    }
    fun fetchStudentList(classId: Int?) {
        Log.d("LOL", "В fetchStudentList")
        viewModelScope.launch {
            _studentList.value = StudentListState(isLoading = true)
            when (val result = getStudentListUseCase.invoke(classId)) {
                is Resource.Success -> {
                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _studentList.value = StudentListState(
                        studentList = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )
//                    _studentList.value.studentList.forEach{
//                        it.gradeList = async {
//                            getGradesByStudents(it.studentId.toInt())
//                        }.await()
//
//                    }
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

            Log.d("LOL", "Error : ${_studentList.value.loadError}, " +
                    "Success : ${_studentList.value.studentList}, " +
                    "Loading : ${_studentList.value.isLoading}")
        }
    }
    suspend fun getGradesByStudents(studentId: Int?): List<Grade> {
        return when (val result = getGradesByStudentUseCase.invoke(studentId)) {
            is Resource.Success -> {
                Log.d("LOL", "is Resource.Success -> ${result.data}")
                result.data ?: emptyList()
            }
            is Resource.Error -> {
                Log.d("LOL", "is Resource.Error -> ${result.message}")
                emptyList()
            }
            is Resource.Loading -> {
                Log.d("LOL", "is Resource.Loading")
                emptyList()
            }
        }
    }





}