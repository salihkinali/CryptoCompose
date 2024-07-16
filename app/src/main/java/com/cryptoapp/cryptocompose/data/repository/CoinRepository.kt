package com.cryptoapp.cryptocompose.data.repository

import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import com.cryptoapp.cryptocompose.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getMarkets(): Flow<NetworkResponse<List<CoinUiModel>>>
}