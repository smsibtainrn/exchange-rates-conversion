package com.smsrn.exchangerate.data.source.local

import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.repository.ExchangeRateLocal
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * smsibtainrn@gmail.com
 */
class ExchangeRateLocalDataSource @Inject constructor(
    private val exchangeRateLocal: ExchangeRateLocal
) : ExchangeRateDataSource {
    override fun saveExchangeRate(exchangeRate: ExchangeRateEntity) {
        exchangeRateLocal.saveExchangeRate(exchangeRate)
    }

    override suspend fun getExchangeRate(currencyCode: String): ExchangeRateEntity {
        return exchangeRateLocal.getExchangeRate(currencyCode)
    }

    override fun isCached(currencyCode: String): Boolean {
        return exchangeRateLocal.isCached(currencyCode)
    }

    override fun setLastCacheTime(lastCache: Long) {
        exchangeRateLocal.setLastCacheTime(lastCache)
    }

    override fun isExpired(): Boolean = exchangeRateLocal.isExpired()
}