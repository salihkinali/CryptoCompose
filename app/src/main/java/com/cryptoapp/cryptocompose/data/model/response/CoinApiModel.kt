package com.cryptoapp.cryptocompose.data.model.response


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CoinApiModel(
    @SerializedName("current_price")
    val currentPrice: BigDecimal?,
    @SerializedName("high_24h")
    val high24h: BigDecimal?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("low_24h")
    val low24h: BigDecimal?,
    @SerializedName("name")
    val name: String?,
)