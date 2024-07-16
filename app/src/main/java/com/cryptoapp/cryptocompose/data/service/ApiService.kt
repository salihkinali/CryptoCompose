package com.cryptoapp.cryptocompose.data.service

import com.cryptoapp.cryptocompose.BuildConfig
import com.cryptoapp.cryptocompose.data.model.response.CoinApiModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_MARKETS = "coins/markets"
private const val QUERY_API_KEY = "x-cg-pro-api-key"

interface ApiService {

    @GET(GET_MARKETS)
    suspend fun getMarkets(
        @Query("vs_currency") currency: String = "usd",
        @Query(QUERY_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): List<CoinApiModel>?
}