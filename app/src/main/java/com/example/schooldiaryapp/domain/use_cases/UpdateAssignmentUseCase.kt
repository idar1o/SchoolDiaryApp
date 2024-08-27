package com.example.schooldiaryapp.domain.use_cases

import com.example.schooldiaryapp.data.source.network.models.AssignmentRequest
import com.example.schooldiaryapp.domain.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateAssignmentUseCase @Inject constructor(
    private val repo: ApiRepository
) {
     operator fun invoke(
        assignmentId: Int,
        assignment: AssignmentRequest
    ): Flow<Boolean> = repo.updateAssignmentById(assignmentId, assignment)
        .flowOn(Dispatchers.IO)
}
