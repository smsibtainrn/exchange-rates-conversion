package com.smsrn.exchangerate.data.source.remote

import com.smsrn.exchangerate.data.mapper.domainEntity.ExchangeRateDomainEntityMapper
import com.smsrn.exchangerate.data.repository.ExchangeRateDataSource
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.data.source.remote.service.ExchangeRateService
import com.smsrn.exchangerate.domain.models.ExchangeRate
import com.smsrn.exchangerate.utils.Utils
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * sraza@adnocdistribution.ae
 */
class ExchangeRateRemoteDataSourceImpl @Inject constructor(
    private val exchangeRateService: ExchangeRateService,
    private val exchangeRateDomainEntityMapper: ExchangeRateDomainEntityMapper
) : ExchangeRateDataSource {
    override suspend fun getExchangeRate(currencyCode: String): ExchangeRateEntity {
        return exchangeRateDomainEntityMapper.toEntity(
            exchangeRateService.fetchHistoricalExchangeRates(Utils.getDate(-1),currencyCode)
        )
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