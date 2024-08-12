package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.domain.WebSocketRepository
import javax.inject.Inject

class ConnectWebSocketUseCase @Inject constructor(
    private val repository: WebSocketRepository
) {
    suspend operator fun invoke(url: String,  senderId: Int, receiverId: Int) = repository.connect(url,  senderId, receiverId)
}