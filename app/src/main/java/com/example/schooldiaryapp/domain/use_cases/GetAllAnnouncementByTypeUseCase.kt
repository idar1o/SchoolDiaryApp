package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.Announcement
import com.example.schooldiaryapp.domain.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAnnouncementByTypeUseCase@Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(type: String): Flow<List<Announcement>> =
        repository.getAllAnnouncementByType(type)
}