package com.example.schooldiaryapp.data.source.network.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class AnnouncementResponse(
    @SerializedName("announce_id")
    val announcementId: Int,
    @SerializedName("receiver_type")
    val receiverType: String,
    @SerializedName("announcement")
    val announcement: String,
    @SerializedName("created_at")
    val createdAt: LocalDate,
)

@RequiresApi(Build.VERSION_CODES.O)
fun AnnouncementResponse.asExternalModel(): Announcement {
    return Announcement(
        announcementId = announcementId,
        receiverType = receiverType,
        announcement = announcement,
        createdAt = createdAt
    )
}

