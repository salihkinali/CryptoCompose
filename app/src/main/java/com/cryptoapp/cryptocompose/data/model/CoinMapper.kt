package com.cryptoapp.cryptocompose.data.model

import com.cryptoapp.cryptocompose.data.model.response.CoinApiModel
import com.cryptoapp.cryptocompose.ui.model.CoinUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject constructor() {

    fun mapToCoinUiModel(apiModel: List<CoinApiModel>?): List<CoinUiModel> {
        return apiModel?.map { model ->
            CoinUiModel(
                id = model.id.orEmpty(),
                name = model.name.orEmpty(),
                date = formatDateString(model.lastUpdated.orEmpty()),
                image = model.image.orEmpty(),
                currentPrice = "$${model.currentPrice}",
                lowCoinAmount = "$${model.low24h}",
                highCoinAmount = "$${model.high24h}",
            )
        }.orEmpty()
    }

    private fun formatDateString(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("HH:mm  dd/MM/yyyy", Locale.getDefault())
        val date: Date? = inputFormat.parse(inputDate)
        return outputFormat.format(date ?: Date())
    }
}

