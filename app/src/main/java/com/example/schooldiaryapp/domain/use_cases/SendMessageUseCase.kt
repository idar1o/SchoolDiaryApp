package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.domain.WebSocketRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: WebSocketRepository
) {
    suspend operator fun invoke(message: WSMessageRequest) = repository.sendMessage(message)
}