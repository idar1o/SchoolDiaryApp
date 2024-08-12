package com.example.schooldiaryapp.data.source.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.schooldiaryapp.data.source.local.models.CachedAssignments
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(assignments: List<CachedAssignments>): List<Long>

    @Query(
        """
            SELECT * FROM ${CachedAssignments.TABLE_NAME}   
        """,
    )
    fun getAll(): Flow<List<CachedAssignments>>

    @Query(
        """
            SELECT * FROM ${CachedAssignments.TABLE_NAME} t
            WHERE t.classId=:classId
            ORDER BY t.deadline
        """,
    )
    fun getCharacter(classId: Long): Flow<List<CachedAssignments>>
}