package com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.data.source.network.models.Announcement
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.convertLongToDate
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.localDateToMillis
import com.example.schooldiaryapp.presentation.components.CircleProgress
import com.example.schooldiaryapp.presentation.navigation.ScreenRoutes

@Composable
fun AnnouncementScreen(navHostController: NavHostController, vm: AnnouncementViewModel) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    AnnouncementScreenContentType(navHostController = navHostController, vm = vm, uiState = uiState)
}

@Composable
fun AnnouncementScreenContentType(navHostController: NavHostController, vm: AnnouncementViewModel, uiState: AnnouncementsState) {
    when(uiState){
        is AnnouncementsState.Error -> {
            uiState.exception
        }
        is AnnouncementsState.Loading -> CircleProgress()
        is AnnouncementsState.Success -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AnnouncementContent(announcements = uiState.tasksList, navHostController = navHostController, vm = vm)
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnnouncementContent(announcements: List<Announcement>, navHostController: NavHostController, vm: AnnouncementViewModel) {
    Box(modifier = Modifier.fillMaxSize()){
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            state = listState
        ) {
            items(
                items = announcements,
            ) { announcement ->
                AnnouncementItem(announcement = announcement) { announcementId ->
                    navHostController.navigate(ScreenRoutes.AnnouncementDetail.createRoute(announcementId))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnnouncementItem(
    announcement: Announcement,
    onItemClick: (announcementId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(announcement.announcementId) },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = announcement.announcement,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = convertLongToDate(localDateToMillis(announcement.createdAt)),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

