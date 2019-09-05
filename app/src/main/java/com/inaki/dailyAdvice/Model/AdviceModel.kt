package com.inaki.dailyAdvice.Model


import com.google.gson.annotations.SerializedName

data class AdviceModel(
    @SerializedName("query")
    val query: String,
    @SerializedName("slips")
    val slips: List<Slip>,
    @SerializedName("total_results")
    val totalResults: String
)