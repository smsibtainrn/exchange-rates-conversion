package com.smsrn.exchangerate.data.source.remote

import com.smsrn.exchangerate.data.mapper.domainEntity.ExchangeRateDomainEntityMapper
import com.smsrn.exchangerate.data.repository.ExchangeRateRemote
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.data.source.remote.service.ExchangeRateService
import com.smsrn.exchangerate.utils.Utils
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */
class ExchangeRateRemoteImpl @Inject constructor(
    private val exchangeRateService: ExchangeRateService,
    private val exchangeRateDomainEntityMapper: ExchangeRateDomainEntityMapper
) : ExchangeRateRemote {
    override suspend fun getExchangeRate(currencyCode: String): ExchangeRateEntity {
        return exchangeRateDomainEntityMapper.toEntity(
            exchangeRateService.fetchHistoricalExchangeRates(Utils.getDate(-1),currencyCode)
        )
    }
}