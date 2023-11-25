package com.smsrn.exchangerate.data.source

import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.source.local.ExchangeRateLocalDataSource
import com.smsrn.exchangerate.data.source.remote.ExchangeRateRemoteDataSource
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * smsibtainrn@gmail.com
 */
class ExchangeRateDataSourceFactory @Inject constructor(
    private val exchangeRateLocalDataSource: ExchangeRateLocalDataSource,
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource
) {
    fun getDataStore(isCached: Boolean): ExchangeRateDataSource {
        return if (isCached && !exchangeRateLocalDataSource.isExpired()) {
            return getLocalDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getLocalDataSource(): ExchangeRateDataSource = exchangeRateLocalDataSource

    fun getRemoteDataSource(): ExchangeRateDataSource = exchangeRateRemoteDataSource
}