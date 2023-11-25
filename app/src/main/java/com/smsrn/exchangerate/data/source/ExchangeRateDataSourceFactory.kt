package com.smsrn.exchangerate.data.source

import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.repository.ExchangeRateLocalDataSource
import com.smsrn.exchangerate.data.source.local.ExchangeRateLocalDataSourceImpl
import com.smsrn.exchangerate.data.source.remote.ExchangeRateRemoteDataSourceImpl
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * sraza@adnocdistribution.ae
 */
class ExchangeRateDataSourceFactory @Inject constructor(
    private val exchangeRateLocalDataSource: ExchangeRateLocalDataSourceImpl,
    private val exchangeRateRemoDataSource: ExchangeRateRemoteDataSourceImpl
) {
    fun getDataStore(isCached: Boolean): ExchangeRateDataSource {
        return if (isCached && !exchangeRateLocalDataSource.isExpired()) {
            return getLocalDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getLocalDataSource(): ExchangeRateDataSource {
        return exchangeRateLocalDataSource
    }

    fun getRemoteDataSource(): ExchangeRateDataSource {
        return exchangeRateRemoDataSource
    }
}