package com.smsrn.exchangerate.data.source.remote

import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.repository.ExchangeRateRemote
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * smsibtainrn@gmail.com
 */
class ExchangeRateRemoteDataSource @Inject constructor(
    private val exchangeRateRemote: ExchangeRateRemote
) : ExchangeRateDataSource {
    override suspend fun getExchangeRate(currencyCode: String): ExchangeRateEntity {
        return exchangeRateRemote.getExchangeRate(currencyCode)
    }

    override fun saveExchangeRate(exchangeRate: ExchangeRateEntity) {
        throw UnsupportedOperationException("saveExchangeRate()")
    }
    override fun isCached(currencyCode: String): Boolean {
        throw UnsupportedOperationException("isCached()")
    }

    override fun setLastCacheTime(lastCache: Long) {
        throw UnsupportedOperationException("setLastCacheTime()")
    }

    override fun isExpired(): Boolean {
        throw UnsupportedOperationException("isExpired()")
    }
}