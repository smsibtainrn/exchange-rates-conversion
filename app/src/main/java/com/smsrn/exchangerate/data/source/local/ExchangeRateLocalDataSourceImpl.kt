package com.smsrn.exchangerate.data.source.local

import com.smsrn.exchangerate.data.mapper.domainEntity.ExchangeRateDomainEntityMapper
import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.source.local.db.ExchangeRatesDao
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.data.source.local.preference.Preferences
import com.smsrn.exchangerate.domain.model.ExchangeRate
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * sraza@adnocdistribution.ae
 */
class ExchangeRateLocalDataSourceImpl @Inject constructor(
    private val preferences: Preferences,
    private val exchangeRatesDao: ExchangeRatesDao,
) : ExchangeRateDataSource {
    override fun saveExchangeRate(exchangeRate: ExchangeRateEntity) {
        exchangeRatesDao.insert(exchangeRate)
        setLastCacheTime(System.currentTimeMillis())
    }

    override suspend fun getExchangeRate(currencyCode: String): ExchangeRateEntity {
        return exchangeRatesDao.getExchangeRates(currencyCode)
    }

    override fun isCached(currencyCode: String): Boolean {
        return exchangeRatesDao.checkExchangeRateExist(currencyCode)
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferences.lastCacheExchangeRatesTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheTimeMillis() = preferences.lastCacheExchangeRatesTime

    companion object {
        /**
         * Expiration time set to 30 minutes
         */
        const val EXPIRATION_TIME = (30 * 1000).toLong()
    }
}