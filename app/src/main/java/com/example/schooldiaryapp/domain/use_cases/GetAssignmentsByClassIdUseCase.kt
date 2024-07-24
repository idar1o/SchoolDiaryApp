package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.network.models.Assignment
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GetAssignmentsByClassIdUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(classId: Int?): Resource<List<Assignment>> =
    apiRepository.getAssignmentsByClassID(classId)

}