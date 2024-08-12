package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.SchoolClass
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject


class GetChatListUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(teacherId: Int): Resource<List<SchoolClass>> =
        repository.getClassesByTeacherId(teacherId)


}