package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.MessageResponse
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class GetMessagesByUsersIdUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(senderId: Int, receiverId: Int): Resource<List<MessageResponse>> =
    apiRepository.getMessagesByUsersID(senderId = senderId, receiverId = receiverId)

}