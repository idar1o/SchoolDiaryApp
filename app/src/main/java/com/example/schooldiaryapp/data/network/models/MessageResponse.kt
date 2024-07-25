package com.example.schooldiaryapp.data.network.models

data class MessageResponse(
    val message_id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val message: String,
    val timestamp: String
)

