package com.cryptoapp.cryptocompose.ui.model

data class CoinUiModel(
    val id: String,
    val currentPrice:String,
    val name : String,
    val date: String,
    val image: String,
    val lowCoinAmount:String,
    val highCoinAmount:String,
)
