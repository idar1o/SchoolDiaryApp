package com.example.schooldiaryapp.presentation.acc_teacher.grade_chat_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.di.UserId
import com.example.schooldiaryapp.data.source.network.models.WSMessageRequest
import com.example.schooldiaryapp.data.source.network.models.WebSocketMessage
import com.example.schooldiaryapp.domain.use_cases.CloseWebSocketUseCase
import com.example.schooldiaryapp.domain.use_cases.ConnectWebSocketUseCase
import com.example.schooldiaryapp.domain.use_cases.GetMessagesByUsersIdUseCase
import com.example.schooldiaryapp.domain.use_cases.ObserveMessagesUseCase
import com.example.schooldiaryapp.domain.use_cases.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GradeChatScreenViewModel @Inject constructor(
    private val getMessagesByUsersIdUseCase: GetMessagesByUsersIdUseCase,
    private val connectWebSocketUseCase: ConnectWebSocketUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val closeWebSocketUseCase: CloseWebSocketUseCase,
    @UserId private val userId: Int
) : ViewModel(){

    private val _messages = MutableStateFlow<Map<LocalDate, List<WebSocketMessage>>>(emptyMap())
    val messages: StateFlow<Map<LocalDate, List<WebSocketMessage>>> = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun observeMessages(senderId: Int){
        viewModelScope.launch {
            observeMessagesUseCase(senderId = senderId, receiverId = userId).collect { message ->
                _messages.value = message.groupBy { it.timestamp.toLocalDate() }
            }
        }
    }
    fun connect(url: String,  senderId: Int) {
        viewModelScope.launch {
            connectWebSocketUseCase(url, senderId = senderId, receiverId = userId)
        }
    }

    fun sendMessage(message: String, senderId: Int) {
        viewModelScope.launch {
            sendMessageUseCase(
                WSMessageRequest(
                    senderId = senderId,
                    receiverId = userId,
                    message = message
                )
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            closeWebSocketUseCase()
        }
    }
}
//private val _messagesList = mutableStateOf(MessagesState())
//val messagesList : State<MessagesState> = _messagesList
//
//val receiverId: Int = userId
//dfdf
//    fun getMessagesForChat(senderId: Int){
//        Log.d("LOL", "В fetchStudentList")
//        viewModelScope.launch {
//            _messagesList.value = MessagesState(isLoading = true)
//            when (val result = getMessagesByUsersIdUseCase.invoke(senderId, userId)) {
//                is Resource.Success -> {
//                    Log.d("LOL", "is Resource.Success -> ${result.data}")
//                    _messagesList.value = MessagesState(
//                        messages = result.data ?: emptyList(),
//                        loadError = "",
//                        isLoading = false
//                    )
//
//                }
//
//                is Resource.Error -> {
//                    Log.d("LOL", "is Resource.Error -> ${result.data}")
//                    _messagesList.value = MessagesState(
//                        loadError = result.message ?: "Unknown error",
//                        isLoading = false
//                    )
//                }
//
//                is Resource.Loading -> {
//                    Log.d("LOL", "is Resource.Loading -> ${result.data}")
//                }
//            }
//
//            Log.d(
//                "LOL", "Error : ${_messagesList.value.loadError}, " +
//                        "Success : ${_messagesList.value.messages}, " +
//                        "Loading : ${_messagesList.value.isLoading}"
//            )
//        }
//    }