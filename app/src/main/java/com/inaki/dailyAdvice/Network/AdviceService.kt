package com.inaki.dailyAdvice.Network

import com.inaki.dailyAdvice.Model.AdviceModel
import com.inaki.dailyAdvice.Model.GameAdviceModel
import com.inaki.dailyAdvice.Util.GAME_ADVICES
import com.inaki.dailyAdvice.Util.RANDOM_ADVICE
import com.inaki.dailyAdvice.Util.SEARCH_ADVICE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AdviceService {

    @GET(RANDOM_ADVICE)
    suspend fun getRandomAdvice(): Response<AdviceModel>

    @GET(SEARCH_ADVICE)
    suspend fun getAdvices(@Path("topic") topic: String): Response<AdviceModel>

    @GET(GAME_ADVICES)
    suspend fun getGameAdvice(@Path("id") id: String): Response<GameAdviceModel>
}