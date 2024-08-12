package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.Student
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GetStudentListUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(classId: Int?): Resource<List<Student>> =
        repository.getStudentsByClassID(classId)

}