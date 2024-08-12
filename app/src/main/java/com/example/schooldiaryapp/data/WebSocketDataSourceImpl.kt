package com.example.schooldiaryapp.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.schooldiaryapp.data.source.network.WebSocketDataSource
import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.LocalDateTime
import javax.inject.Inject

class WebSocketDataSourceImpl @Inject constructor(
    private val client: OkHttpClient
) : WebSocketDataSource {
    private val _messages = MutableStateFlow<List<WebSocketMessage>>(emptyList())
    private var webSocket: WebSocket? = null
    @RequiresApi(Build.VERSION_CODES.O)
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun connect(url: String, senderId: Int, receiverId: Int) {
        webSocket?.close(1000, "Reconnecting")

        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                scope.launch {
                    sendMessage(message =
                    WSMessageRequest(
                        senderId = senderId,
                        receiverId = receiverId,
                        ""
                    )
                    )
                }


            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMessage(webSocket: WebSocket, text: String) {
                scope.launch {
                    try {
                        val messageType = object : TypeToken<List<WebSocketMessage>>() {}.type
                        val messages: List<WebSocketMessage> = gson.fromJson(text, messageType)
                        _messages.emit(messages)
                        Log.d("LOL", "Successfully data arrived")
                    } catch (e: Exception) {
                        // Логирование или обработка ошибки парсинга
                        Log.d("LOL", "Error parsing message: ${e.message}")
                    }
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                scope.launch {
                    val errorMessage = WebSocketMessage(
                        messageId = -1,
                        senderId = -1,
                        receiverId = -1,
                        message = "Error: ${t.message}",
                        timestamp = LocalDateTime.now()
                    )
                    _messages.emit(listOf(errorMessage))
                    Log.d("LOL", "onFailure data arrived")

                }

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)


            }
        })
    }

    override suspend fun sendMessage(message: WSMessageRequest) {
        val jsonMessage = gson.toJson(message)
        webSocket?.send(jsonMessage)
    }

    override fun observeMessages(senderId: Int, receiverId: Int): StateFlow<List<WebSocketMessage>> {
        return _messages
    }


    override suspend fun close() {
        webSocket?.close(1000, "Client closed")
        webSocket = null
        Log.d("LOL", "Client closed")
    }



}

