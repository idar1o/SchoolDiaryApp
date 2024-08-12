package com.example.schooldiaryapp.data.source.network

import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import com.example.schooldiaryapp.domain.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    private val dataSource: WebSocketDataSource
) : WebSocketRepository {
    override suspend fun connect(url: String, senderId: Int, receiverId: Int) = dataSource.connect(url,  senderId, receiverId)
    override suspend fun sendMessage(message: WSMessageRequest) = dataSource.sendMessage(message)
    override fun observeMessages( senderId: Int, receiverId: Int): Flow<List<WebSocketMessage>> = dataSource.observeMessages( senderId, receiverId)
    override suspend fun close() = dataSource.close()
}