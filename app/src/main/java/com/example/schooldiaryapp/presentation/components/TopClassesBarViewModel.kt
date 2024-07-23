package com.example.schooldiaryapp.presentation.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.domain.use_cases.GetChatListUseCase
import com.example.schooldiaryapp.presentation.models.ClassListState
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopClassesBarViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel() {
    private val _classList = mutableStateOf(ClassListState())
    val classList: State<ClassListState> = _classList

    private val _selectedItem = MutableStateFlow<String?>(null)
    val selectedItem: StateFlow<String?> = _selectedItem

    fun selectItem(item: String) {
        _selectedItem.value = item
        Log.d("LOL", "selectItem = $item")
    }

    val teacherId = 4
    init {
        fetchClassList(teacherId)
    }

    private fun fetchClassList(teacherId: Int) {
        Log.d("LOL", "В fetchClassList")
        viewModelScope.launch {
            _classList.value = ClassListState(isLoading = true)
            when (val result = getChatListUseCase.invoke(teacherId)) {
                is Resource.Success -> {

                    Log.d("LOL", "is Resource.Success -> ${result.data}")
                    _classList.value = ClassListState(
                        classList = result.data ?: emptyList(),
                        loadError = "",
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    Log.d("LOL", "is Resource.Error -> ${result.data}")
                    _classList.value = ClassListState(
                        loadError = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    Log.d("LOL", "is Resource.Loading -> ${result.data}")

                }
            }

            Log.d(
                "LOL", "Error : ${_classList.value.loadError}, " +
                        "Success : ${_classList.value.classList}, " +
                        "Loading : ${_classList.value.isLoading}"
            )
        }
    }

}