package com.cryptoapp.cryptocompose.utils

sealed class NetworkResponse<out T : Any> {
    data object Loading : NetworkResponse<Nothing>()
    data class Success<out T : Any>(val result: T) : NetworkResponse<T>()
    data class Error(val exception: Exception) : NetworkResponse<Nothing>()
}