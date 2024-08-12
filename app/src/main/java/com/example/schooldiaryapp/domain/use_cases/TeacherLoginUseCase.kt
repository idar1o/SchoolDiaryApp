package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.LoginRequest
import com.example.schooldiaryapp.data.source.network.models.LoginResponse
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class TeacherLoginUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(loginData : LoginRequest) : Resource<LoginResponse?> =
        apiRepository.teacherLogin(loginData)

}