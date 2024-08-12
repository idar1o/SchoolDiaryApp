package com.example.schooldiaryapp.data.source.local

import com.example.schooldiaryapp.data.source.local.models.CachedAssignments
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getAssignment(input: String, limit: Int, offset: Int): Flow<List<CachedAssignments>>

    suspend fun storeAssignments(characters: List<CachedAssignments>)
}