package com.inaki.dailyAdvice.Model


import com.google.gson.annotations.SerializedName

data class Slip(
    @SerializedName("advice")
    val advice: String,
    @SerializedName("slip_id")
    val slipId: String
)