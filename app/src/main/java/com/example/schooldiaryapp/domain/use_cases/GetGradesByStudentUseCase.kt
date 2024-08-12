package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.Grade
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GetGradesByStudentUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(studentId: Int?): Resource<List<Grade>> =
        repository.getGradesByStudentID(studentId)

}