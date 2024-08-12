package com.example.schooldiaryapp.presentation.acc_teacher.grade_chat_screen

import com.example.schooldiaryapp.data.source.network.models.MessageResponse

data class MessagesState(
    val messages : List<MessageResponse> = listOf(),
    val loadError : String = "",
    val isLoading: Boolean = false
)
