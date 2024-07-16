package com.cryptoapp.cryptocompose.data.usecase

import com.cryptoapp.cryptocompose.data.repository.CoinRepository
import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import com.cryptoapp.cryptocompose.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
   suspend fun invoke(): Flow<NetworkResponse<List<CoinUiModel>>> =
        coinRepository.getMarkets()
}