package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("username")
    val username: String
)