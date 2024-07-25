package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("teacher_id")
    val userId: Int,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("username")
    val username: String
)