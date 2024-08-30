package com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.data.source.network.models.Announcement
import com.example.schooldiaryapp.presentation.components.CircleProgress
import com.example.schooldiaryapp.presentation.components.PullToRefreshBox
import com.example.schooldiaryapp.presentation.navigation.BottomBarRoutes
import com.example.schooldiaryapp.ui.theme.AppBarBgColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnnouncementDetailScreen(vm: AnnouncementDetailViewModel, navHostController: NavHostController){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    TypeAnnouncementContent(uiState = uiState, navHostController = navHostController, vm)
}

@Composable
fun TypeAnnouncementContent(uiState: AnnouncementDetailState, navHostController: NavHostController, vm: AnnouncementDetailViewModel){
    when(uiState){
        is AnnouncementDetailState.Error -> {
            uiState.exception
        }
        is AnnouncementDetailState.Loading -> CircleProgress()
        is AnnouncementDetailState.Success -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AnnouncementDetailContent(assignment = uiState.data, navHostController = navHostController, vm)
            }
        }
        is AnnouncementDetailState.Init -> {

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementDetailContent(
    assignment: Announcement,
    navHostController: NavHostController,
    vm: AnnouncementDetailViewModel,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Объявление",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                navHostController.navigate(BottomBarRoutes.ANNOUNCEMENT.routes)
                            },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
                backgroundColor = AppBarBgColor
            )
        },
        content = { _ ->

            val isRefreshing by vm.isRefreshing.collectAsStateWithLifecycle()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PullToRefreshBox(
                    item = assignment,
                    content = { announcement ->

                        Text(text = announcement.announcement)
                        Text(text = "${announcement.createdAt}")
                    },
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        vm.refreshAnnouncement()
                    }
                )
            }
        }
    )
}