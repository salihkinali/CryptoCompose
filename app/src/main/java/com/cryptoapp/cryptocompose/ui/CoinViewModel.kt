package com.cryptoapp.cryptocompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.cryptocompose.data.usecase.GetMarketUseCase
import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import com.cryptoapp.cryptocompose.ui.model.CoinUiState
import com.cryptoapp.cryptocompose.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val getMarketUseCase: GetMarketUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<CoinUiState>(CoinUiState.Init)
    val uiState = _uiState.asStateFlow()


    fun getMarkets() {
        viewModelScope.launch {
            getMarketUseCase.invoke().collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        _uiState.emit(CoinUiState.Error(it.exception.message.toString()))
                    }

                    NetworkResponse.Loading -> {
                        _uiState.emit(CoinUiState.Loading)
                    }

                    is NetworkResponse.Success -> {
                        _uiState.emit(CoinUiState.Success(it.result))
                    }
                }

            }
        }
    }

}