package com.smsrn.exchangerate.data.repository

import com.smsrn.exchangerate.data.source.ExchangeRateDataSourceFactory
import com.smsrn.exchangerate.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */
class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateSourceFactory: ExchangeRateDataSourceFactory,
) : ExchangeRateRepository {

    override suspend fun fetchHistoricalExchangeRates(currencyCode: String) = flow {
        emit(exchangeRateSourceFactory.getLocalDataSource().getExchangeRate(currencyCode))
    }

    override suspend fun fetchHistoricalExchangeRates(currencyCode: String, date: String) = flow {
        val isCache =
            exchangeRateSourceFactory.getLocalDataSource().isCached(currencyCode = currencyCode)
        val exchangeRate =
            exchangeRateSourceFactory.getDataStore(isCache).getExchangeRate(currencyCode)
        exchangeRateSourceFactory.getLocalDataSource().saveExchangeRate(exchangeRate)
        emit(exchangeRate)
    }
}