package com.example.schooldiaryapp.data.source.local.models


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.schooldiaryapp.data.source.local.models.CachedAssignments.Companion.TABLE_NAME
import com.example.schooldiaryapp.domain.models.Assignment
import java.time.LocalDate
@Keep
@Entity(tableName = TABLE_NAME)
data class CachedAssignments(
    @PrimaryKey(autoGenerate = false)
    val assignmentId: Int,
    val classId: Int,
    val deadline: LocalDate,
    val description: String,
    val subjectName: String,
    val studentsId: List<Int>,
    val title: String,
    val studentNames: List<String>
){

    companion object {
        const val TABLE_NAME = "assignments"
    }
}


fun CachedAssignments.asExternalModel(): Assignment {
    return Assignment(
        assignmentId = assignmentId,
        classId = classId,
        deadline = deadline,
        description = description,
        subjectName = subjectName,
        studentsId = studentsId,
        title = title,
        studentNames = studentNames
    )
}

fun Assignment.asEntity(): CachedAssignments {
    return CachedAssignments(
        assignmentId = assignmentId,
        classId = classId,
        deadline = deadline,
        description = description,
        subjectName = subjectName,
        studentsId = studentsId,
        title = title,
        studentNames = studentNames
    )
}

