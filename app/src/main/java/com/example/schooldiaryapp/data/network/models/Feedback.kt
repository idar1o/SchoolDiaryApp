package com.example.schooldiaryapp.data.network.models

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("String")
    var data: String,
    @SerializedName("Valid")
    var valid: Boolean,
    )