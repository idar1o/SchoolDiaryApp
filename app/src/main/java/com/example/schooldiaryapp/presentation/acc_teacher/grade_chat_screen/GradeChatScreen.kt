package com.example.schooldiaryapp.presentation.acc_teacher.grade_chat_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.presentation.navigation.BottomBarRoutes
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Message(
    val text: String,
    val isSentByUser: Boolean, // true если сообщение отправлено пользователем, false если получено
    val timestamp: LocalDateTime
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GradeChatScreen(
    navHostController: NavHostController,
    vm: GradeChatScreenViewModel,
    senderId: Int,
    senderName: String
) {
    LaunchedEffect(senderId) {
        vm.connect("ws://26.85.111.145:8080/ws", senderId)
        vm.observeMessages(senderId)
        Log.d("LOL", "LaunchedEffect : $senderId")
    }

    val messages by vm.messages.collectAsState()
    var message by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages) {
        snapshotFlow { messages.size }
            .collect {
                if (it > 0) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(it - 1)
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Text(
                        text = senderName,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                navHostController.navigate(BottomBarRoutes.GRADES.routes)
                            },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO: Implement Search Action */ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
                    }
                    IconButton(onClick = { /* TODO: Implement More Options Action */ }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More options", tint = Color.White)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFECE5DD))
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val groupedMessages = messages.entries.sortedBy { it.key }.map { (date, msgs) ->
                        date to msgs
                    }

                    items(groupedMessages) { (date, msgs) ->
                        DateTimeItem(dateTime = formatDate(date))
                        msgs.forEach { message ->
                            MessageItem(message =
                            Message(
                                text = message.message,
                                isSentByUser = (message.senderId == senderId),
                                timestamp = message.timestamp
                            )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Button(
                        onClick = {
                            vm.sendMessage(message, senderId)
                            message = ""
                        }
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    )


}
@Composable
fun DateTimeItem(dateTime: String){

    Box(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Text(
            text = dateTime,
            color = Color.Gray,
            modifier = Modifier.padding(4.dp)
        )
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (message.isSentByUser) Color(0xFFDCF8C6) else Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .widthIn(max = 240.dp) // Ограничение ширины сообщения
        ) {
            Text(text = message.text)
            Text(
                text = formatTime( message.timestamp),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(localDateTime: LocalDateTime): String {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("HH:mm")
    } else {
        null
    }
    return localDateTime.format(formatter)
}
@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(localDateTime: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    return localDateTime.format(formatter)
}