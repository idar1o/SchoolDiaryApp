package com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.source.network.models.Announcement
import com.example.schooldiaryapp.domain.use_cases.GetAnnouncementByIdUseCase
import com.example.schooldiaryapp.presentation.acc_teacher.task_item_screen.navigation.AnnouncementArgs
import com.example.schooldiaryapp.utils.Result
import com.example.schooldiaryapp.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

sealed interface AnnouncementDetailState{

    data class Success( val data: Announcement) : AnnouncementDetailState

    data class Error(val exception: Throwable? = null) : AnnouncementDetailState
    data object Loading : AnnouncementDetailState
    data class Init( val data: Announcement) : AnnouncementDetailState
}
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AnnouncementDetailViewModel @Inject constructor(
    val getAnnouncementByIdUseCase: GetAnnouncementByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){
    private val args: AnnouncementArgs by lazy { AnnouncementArgs(savedStateHandle = savedStateHandle) }
    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState : MutableStateFlow<AnnouncementDetailState> = MutableStateFlow(
        AnnouncementDetailState.Init(
            Announcement(
                announcementId = 0,
                announcement = "",
                createdAt = LocalDate.MIN,
                receiverType = ""
            )
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<AnnouncementDetailState> = _uiState

    // Состояние обновления (свайп-обновление)
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        // Инициализация данных при создании ViewModel
        refreshAnnouncement()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshAnnouncement() {
        _isRefreshing.value = true
        viewModelScope.launch {
            getAnnouncementByIdUseCase(args.announceId)
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Loading -> AnnouncementDetailState.Loading
                        is Result.Success -> AnnouncementDetailState.Success(result.data)
                        is Result.Error -> AnnouncementDetailState.Error(result.exception)
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
            _isRefreshing.value = false
        }
    }

}