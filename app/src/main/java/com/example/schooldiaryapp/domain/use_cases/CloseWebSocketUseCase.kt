package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.domain.WebSocketRepository
import javax.inject.Inject

class CloseWebSocketUseCase @Inject constructor(
    private val repository: WebSocketRepository
) {
    suspend operator fun invoke() = repository.close()
}