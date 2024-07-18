package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GradeTheStudentUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(grade: Grade?): Resource<Unit> =
        repository.addStudentGrade(grade)

}