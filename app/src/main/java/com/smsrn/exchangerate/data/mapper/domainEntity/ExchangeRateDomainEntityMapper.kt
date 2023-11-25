package com.smsrn.exchangerate.data.mapper.domainEntity

import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.domain.model.ExchangeRate
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/23/2023.
 * sraza@adnocdistribution.ae
 */
class ExchangeRateDomainEntityMapper @Inject constructor() {

    fun toEntity(exchangeRate: ExchangeRate) =
        ExchangeRateEntity(exchangeRate.base, exchangeRate.rates)
}