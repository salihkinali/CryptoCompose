package com.cryptoapp.cryptocompose.ui.model

sealed class CoinUiState {
    data object Init: CoinUiState()
    data object Loading : CoinUiState()
    data class Error(val errorMessage: String): CoinUiState()
    data class Success(val list: List<CoinUiModel>): CoinUiState()
}