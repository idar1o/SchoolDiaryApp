package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

// WebSocketMessage.kt
data class WebSocketMessage(
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("message_id")
    val messageId: Int? = null,
    @SerializedName("timestamp")
    val timestamp: LocalDateTime
    )

