package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.network.models.Teacher
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GetSubjectsForStudentUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(classId: Int?): Resource<List<Teacher>> =
        repository.getTeachersByClass(classId)

}