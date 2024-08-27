package com.example.schooldiaryapp.data.source.network.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schooldiaryapp.domain.models.Assignment
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class AssignmentResponse(
    @SerializedName("assignment_id")
    val assignmentId: Int?,
    @SerializedName("class_id")
    val classId: Int?,
    @SerializedName("subject_id")
    val subjectId: Int?,
    @SerializedName("deadline")
    val deadline: LocalDate?,
    @SerializedName("assignment_day")
    val assignmentDay: LocalDate?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("subject_name")
    val subjectName: String?,
    @SerializedName("students_id")
    val studentsId: List<Int>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("student_names")
    val studentNames: String?
)

@RequiresApi(Build.VERSION_CODES.O)
fun AssignmentResponse.asExternalModel(): Assignment {
    return Assignment(
        assignmentId = assignmentId ?: -1,
        classId = classId ?: -1,
        deadline = deadline ?: LocalDate.MIN,
        description = description ?: "",
        subjectName = subjectName ?: "",
        studentsId = studentsId ?: listOf(),
        title = title ?: "",
        studentNames = studentNames?.split(", ")?.map { it.trim() } ?: listOf(),
        assignmentDay = assignmentDay ?: LocalDate.MIN,
        subjectId = subjectId ?: -1
    )
}