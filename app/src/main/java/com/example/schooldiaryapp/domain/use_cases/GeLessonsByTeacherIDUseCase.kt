package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.network.models.WeeklySchedules
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GeLessonsByTeacherIDUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(teacherId: Int?): Resource<WeeklySchedules> =
    apiRepository.getLessonsByTeacherID(teacherId)

}