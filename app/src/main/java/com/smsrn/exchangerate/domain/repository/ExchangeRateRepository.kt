package com.smsrn.exchangerate.domain.repository

import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */
interface ExchangeRateRepository {

    suspend fun fetchHistoricalExchangeRates(
        currencyCode: String
    ): Flow<ExchangeRateEntity>

    suspend fun fetchHistoricalExchangeRates(
        currencyCode: String, date: String
    ): Flow<ExchangeRateEntity>
}