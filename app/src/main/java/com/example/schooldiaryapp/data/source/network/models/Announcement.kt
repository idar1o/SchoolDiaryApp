package com.example.schooldiaryapp.data.source.network.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Announcement(
    val announcementId: Int,
    val receiverType: String,
    val announcement: String,
    val createdAt: LocalDate
)

@RequiresApi(Build.VERSION_CODES.O)
fun Announcement.asExternalModel(): AnnouncementResponse {
    return AnnouncementResponse(
        announcementId = announcementId,
        receiverType = receiverType,
        announcement = announcement,
        createdAt = createdAt
    )
}