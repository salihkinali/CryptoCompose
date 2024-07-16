package com.cryptoapp.cryptocompose.data.repository

import com.cryptoapp.cryptocompose.data.di.IoDispatcher
import com.cryptoapp.cryptocompose.data.model.CoinMapper
import com.cryptoapp.cryptocompose.data.service.ApiService
import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import com.cryptoapp.cryptocompose.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val uiMapper: CoinMapper

) : CoinRepository {
    override suspend fun getMarkets(): Flow<NetworkResponse<List<CoinUiModel>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val response = apiService.getMarkets()
                emit(NetworkResponse.Success(uiMapper.mapToCoinUiModel(response)))
            } catch (e: Exception) {
                emit(NetworkResponse.Error(e))
            }

        }.flowOn(ioDispatcher)
    }
}