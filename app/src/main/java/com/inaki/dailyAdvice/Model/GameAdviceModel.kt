package com.inaki.dailyAdvice.Model


import com.google.gson.annotations.SerializedName

data class GameAdviceModel(
    @SerializedName("slip")
    val slip: Slip
)