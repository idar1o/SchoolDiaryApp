package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Grade(
    @SerializedName("student_id")
    var studentId: Int,
    @SerializedName("grade")
    var grade: Int,
    @SerializedName("feedback")
    var feedBack: Feedback,
    @SerializedName("subjectId")
    var subjectId: Int,
    @SerializedName("gradeDt")
    var gradeDt: LocalDateTime,
)
