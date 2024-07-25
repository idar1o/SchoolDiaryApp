package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName

data class WeekSchedule(
    @SerializedName("monday") val monday: List<Lesson>,
    @SerializedName("tuesday") val tuesday: List<Lesson>,
    @SerializedName("wednesday") val wednesday: List<Lesson>,
    @SerializedName("thursday") val thursday: List<Lesson>,
    @SerializedName("friday") val friday: List<Lesson>,
    @SerializedName("saturday") val saturday: List<Lesson>
)

typealias WeeklySchedules = Map<String, WeekSchedule>
