package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class WSMessageRequest(
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("message")
    val message: String = ""
)
