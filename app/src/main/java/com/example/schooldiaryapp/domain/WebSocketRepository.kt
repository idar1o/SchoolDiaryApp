package com.example.schooldiaryapp.domain

import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    suspend fun connect(url: String, senderId: Int, receiverId: Int)
    suspend fun sendMessage(message: WSMessageRequest)
    fun observeMessages( senderId: Int, receiverId: Int): Flow<List<WebSocketMessage>>
    suspend fun close()
}
