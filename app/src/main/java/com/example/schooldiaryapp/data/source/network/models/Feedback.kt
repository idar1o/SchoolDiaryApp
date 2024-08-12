package com.example.schooldiaryapp.data.source.network.models

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("String")
    var data: String,
    @SerializedName("Valid")
    var valid: Boolean,
    )