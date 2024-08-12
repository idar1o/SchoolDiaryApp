package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import com.example.schooldiaryapp.domain.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// ObserveMessagesUseCase.kt
class ObserveMessagesUseCase @Inject constructor(
    private val repository: WebSocketRepository
) {
    operator fun invoke(senderId: Int, receiverId: Int): Flow<List<WebSocketMessage>> = repository.observeMessages( senderId, receiverId)
}