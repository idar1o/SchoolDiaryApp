package com.example.schooldiaryapp.data.source.network

import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import kotlinx.coroutines.flow.SharedFlow

interface WebSocketDataSource {
    suspend fun connect(url: String,senderId: Int, receiverId: Int)
    suspend fun sendMessage(message: WSMessageRequest)
    fun observeMessages(senderId: Int, receiverId: Int): SharedFlow<List<WebSocketMessage>>
    suspend fun close()
}
