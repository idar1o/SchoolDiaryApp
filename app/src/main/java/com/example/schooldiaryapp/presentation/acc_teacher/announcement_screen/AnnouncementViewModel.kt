package com.example.schooldiaryapp.presentation.acc_teacher.announcement_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.source.network.models.Announcement
import com.example.schooldiaryapp.domain.use_cases.GetAllAnnouncementByTypeUseCase
import com.example.schooldiaryapp.utils.Result
import com.example.schooldiaryapp.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AnnouncementsState{

    data class Success(val tasksList: List<Announcement>) : AnnouncementsState

    data class Error(val exception: Throwable? = null) : AnnouncementsState
    data object Loading : AnnouncementsState
}
@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    val getAllAnnouncementByTypeUseCase: GetAllAnnouncementByTypeUseCase
): ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _uiState : MutableStateFlow<AnnouncementsState> = MutableStateFlow(
        AnnouncementsState.Loading
    )
    val uiState: StateFlow<AnnouncementsState> = _uiState

    init {
        getAnnouncements()
    }
    private fun getAnnouncements(){
        Log.d("LOL", "fun getAnnouncements")
        _isRefreshing.value = true
        viewModelScope.launch {
            getAllAnnouncementByTypeUseCase("teacher")
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d("LOL","AnnouncementsState.Loading")
                            AnnouncementsState.Loading
                        }
                        is Result.Success -> {
                            Log.d("LOL","AnnouncementsState.Success${result.data}")
                            AnnouncementsState.Success(result.data)

                        }
                        is Result.Error -> {
                            Log.d("LOL", "AnnouncementsState.Error ${result.exception}")
                            AnnouncementsState.Error(result.exception)

                        }
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
            _isRefreshing.value = false
        }
    }
}